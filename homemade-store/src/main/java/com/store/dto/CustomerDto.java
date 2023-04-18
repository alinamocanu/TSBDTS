package com.store.dto;

import com.store.validator.OnlyDigits;
import com.store.validator.OnlyLetters;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private Long customerId;

    @Size(min = 1, max = 50)
    @OnlyLetters
    private String firstName;

    @OnlyLetters
    @Size(min = 1, max = 50)
    private String lastName;

    @NotEmpty
    @OnlyLetters
    @Size(min = 0, max = 300)
    private String address;

    @OnlyDigits
    @Size(min = 10, max = 12)
    private String phoneNumber;
}
