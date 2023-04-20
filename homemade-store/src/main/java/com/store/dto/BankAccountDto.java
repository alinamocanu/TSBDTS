package com.store.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.store.domain.Customer;
import com.store.validator.OnlyDigits;
import com.store.validator.OnlyDigitsAndSpaces;
import lombok.*;

import javax.validation.constraints.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDto {

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 16, max = 19)
    @OnlyDigitsAndSpaces
    private String cardNumber;

    @Min(100)
    @Max(999)
    private int bankAccountCVV;

    @Min(0)
    private double balance;

    @JsonIgnore
    private Customer customer;
}
