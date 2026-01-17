package com.github.hondams.fw.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.hondams.fw.validation.constraintvalidators.DelegatingFormValidator;
import com.github.hondams.fw.validation.constraintvalidators.FormValidatorBase;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(FormValidation.List.class)
@Constraint(validatedBy = DelegatingFormValidator.class)
@Documented
public @interface FormValidation {

    String message() default "{com.github.hondams.fw.validation.CustomValidation.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends FormValidatorBase> validatorType();

    FormValidationParam[] params() default {};

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        FormValidation[] value();
    }
}
