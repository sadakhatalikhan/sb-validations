package com.sb.sdjpa.crud.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder(toBuilder = true)
public class CustomerRequest {

    @JsonProperty("customerName")
    @NotBlank(message = "Please provide username")
    private String customerName;

    @JsonProperty("customerAge")
    private int customerAge;

    @JsonProperty("customerMobileNumber")
    @NotBlank(message = "Please provide customerMobileNumber")
    @Size(min = 10, max = 10, message = "Please provide the valid mobile number")
    private String customerMobileNumber;

    @JsonProperty("customerEmailAddress")
    @NotBlank(message = "Please provide customerEmailAddress")
    @Email(message = "please provide valid email address")
    private String customerEmailAddress;

    @JsonProperty("customerAddress")
    @Valid
    private CustomerAddress customerAddress;
}
