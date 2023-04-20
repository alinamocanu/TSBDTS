package com.store.dto;

import com.store.validator.OnlyDigits;
import com.store.validator.OnlyLetters;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private Long customerId;

    @Email
    private String email;

    @Length(min = 4, max = 100)
    private String username;

    @Length(min = 4, max = 100)
    private String password;

    @OnlyLetters
    @Length(min = 1, max = 100)
    private String firstName;

    @OnlyLetters
    @Length(min = 1, max = 50)
    private String lastName;

    @NotEmpty
    @OnlyLetters
    @Length(min = 0, max = 300)
    private String address;

}
