package com.store.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OnlyDigitsValidator implements ConstraintValidator<OnlyDigits, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value.matches("[0-9]+");
    }
}

