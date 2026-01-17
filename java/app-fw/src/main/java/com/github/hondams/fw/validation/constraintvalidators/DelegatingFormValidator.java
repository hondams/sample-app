package com.github.hondams.fw.validation.constraintvalidators;

import com.github.hondams.fw.validation.constraints.FormValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DelegatingFormValidator implements ConstraintValidator<FormValidation, Object> {

    private FormValidatorBase validator;

    @Override
    public void initialize(FormValidation constraintAnnotation) {
        try {
            this.validator = constraintAnnotation.validatorType().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
        this.validator.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return this.validator.isValid(value, context);
    }

}
