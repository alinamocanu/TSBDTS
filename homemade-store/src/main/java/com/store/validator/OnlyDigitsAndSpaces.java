package com.store.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OnlyDigitsAndSpacesValidator.class)
@Documented
public @interface OnlyDigitsAndSpaces {

    String message() default "Only digits and spaces required";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}