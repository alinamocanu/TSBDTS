package com.store.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OnlyLettersValidator implements ConstraintValidator<OnlyLetters, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value.matches("[a-zA-Z]+");
    }
}
