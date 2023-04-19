package com.store.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.store.domain.Customer;
import com.store.validator.OnlyDigits;
import com.store.validator.OnlyDigitsAndSpaces;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @Size(min = 3, max = 3)
    private int bankAccountCVV;

    @OnlyDigits
    @Size(min = 1)
    private double balance;

    @JsonIgnore
    private Customer customer;
}
