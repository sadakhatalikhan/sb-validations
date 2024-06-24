package com.sb.sdjpa.crud.service.impl;

import com.sb.sdjpa.crud.model.CustomerModel;
import com.sb.sdjpa.crud.repository.CustomerRepository;
import com.sb.sdjpa.crud.request.CustomerRequest;
import com.sb.sdjpa.crud.response.APIResponse;
import com.sb.sdjpa.crud.response.CustomerResponse;
import com.sb.sdjpa.crud.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.sb.sdjpa.crud.constants.AppConstants.*;
import static com.sb.sdjpa.crud.mapper.CustomerMapper.modelToResponseMapper;
import static com.sb.sdjpa.crud.mapper.CustomerMapper.requestToModel;

/**
 * This class holds all the business logic, and it will be used as a interface between controller and DAO layer.
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * This method is used to store the customer details into the database.
     *
     * @param request customer request
     * @return responseEntity object
     */
    @Override
    public ResponseEntity<APIResponse> createCustomer(CustomerRequest request) {

        CustomerModel customerModel = customerRepository.save(requestToModel(request));

        return ResponseEntity.ok(
                APIResponse.builder()
                        .errorCode(SUCCESS_CODE)
                        .errorMessage(SUCCESSFULLY_STORED)
                        .data(modelToResponseMapper(customerModel))
                        .build()
        );
    }

    /**
     * This method is used to fetch all the customers from the database.
     *
     * @return responseEntity object
     */
    @Override
    public ResponseEntity<APIResponse> getAllCustomers() {
        List<CustomerModel> customerDetails = customerRepository.findAll();
        List<CustomerResponse> customers = customerDetails.stream()
                .map(customerModel -> modelToResponseMapper(customerModel))
                .toList();

        return ResponseEntity.ok(
                APIResponse.builder()
                        .errorCode(SUCCESS_CODE)
                        .errorMessage(SUCCESSFULLY_RETRIEVED)
                        .data(customers)
                        .build()
        );
    }

    /**
     * Fetch customer based on the specific id.
     *
     * @param customerId customer id
     * @return responseEntity object
     */
    @Override
    public ResponseEntity<APIResponse> getByCustomerId(long customerId) {
        Optional<CustomerModel> modelOptional = customerRepository.findById(customerId);
        if (!modelOptional.isPresent()) {
            return ResponseEntity.ok(
                    APIResponse.builder()
                            .errorCode(CUSTOMER_NOT_EXISTS_CODE)
                            .errorMessage(CUSTOMER_NOT_EXISTS)
                            .data(List.of())
                            .build()
            );
        }

        CustomerModel model = modelOptional.get();
        CustomerResponse response = modelToResponseMapper(model);
        return ResponseEntity.ok(
                APIResponse.builder()
                        .errorCode(SUCCESS_CODE)
                        .errorMessage(SUCCESSFULLY_RETRIEVED)
                        .data(response)
                        .build()
        );
    }

    /**
     * This method is used to delete the customer from the database.
     *
     * @param customerId customer id
     * @return responseEntity object
     */
    @Override
    public ResponseEntity<APIResponse> deleteByCustomerId(long customerId) {

        Optional<CustomerModel> modelOptional = customerRepository.findById(customerId);

        if (!modelOptional.isPresent()) {
            return ResponseEntity.ok(
                    APIResponse.builder()
                            .errorCode(CUSTOMER_NOT_EXISTS_CODE)
                            .errorMessage(CUSTOMER_NOT_EXISTS)
                            .data(List.of())
                            .build()
            );
        }
        customerRepository.deleteById(customerId);
        return ResponseEntity.ok(
                APIResponse.builder()
                        .errorCode(SUCCESS_CODE)
                        .errorMessage(SUCCESSFULLY_DELETED)
                        .data(List.of())
                        .build()
        );
    }

    /**
     * This method is used to update the customer details into the database.
     *
     * @param customerId customer id
     * @param request customer request object
     * @return responseEntity object
     */
    @Override
    public ResponseEntity<APIResponse> updateCustomerDetails(long customerId, CustomerRequest request) {

        Optional<CustomerModel> modelOptional = customerRepository.findById(customerId);
        if (modelOptional.isPresent()) {
            CustomerModel model = modelOptional.get();
            model.setCustomerName(request.getCustomerName());
            model.setCustomerAge(request.getCustomerAge());
            model.setCustomerMobileNumber(request.getCustomerMobileNumber());
            model.setCustomerEmailAddress(request.getCustomerEmailAddress());
           // model.setCustomerAddress(request.getCustomerAddress());
            model = customerRepository.save(model);

            return ResponseEntity.ok(
                    APIResponse.builder()
                            .errorCode(SUCCESS_CODE)
                            .errorMessage(SUCCESSFULLY_UPDATED)
                            .data(modelToResponseMapper(model))
                            .build()
            );
        } else {
            return ResponseEntity.ok(
                    APIResponse.builder()
                            .errorCode(CUSTOMER_NOT_EXISTS_CODE)
                            .errorMessage(CUSTOMER_NOT_EXISTS)
                            .data(List.of())
                            .build()
            );
        }
    }
}
