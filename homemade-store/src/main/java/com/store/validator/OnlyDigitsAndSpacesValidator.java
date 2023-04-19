package com.store.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OnlyDigitsAndSpacesValidator implements ConstraintValidator<OnlyDigitsAndSpaces, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value.matches("[0-9 ]+");
    }
}
