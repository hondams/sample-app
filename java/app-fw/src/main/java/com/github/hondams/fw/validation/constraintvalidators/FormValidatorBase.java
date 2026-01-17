package com.github.hondams.fw.validation.constraintvalidators;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.github.hondams.fw.validation.constraints.FormValidation;
import com.github.hondams.fw.validation.constraints.FormValidationParam;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class FormValidatorBase implements ConstraintValidator<FormValidation, Object> {

    @Getter(AccessLevel.PROTECTED)
    private Map<String, String> params;

    @Override
    public void initialize(FormValidation constraintAnnotation) {
        this.params = createParams(constraintAnnotation);
    }

    private Map<String, String> createParams(FormValidation constraintAnnotation) {
        Map<String, String> map = new LinkedHashMap<>();
        for (FormValidationParam param : constraintAnnotation.params()) {
            map.put(param.paramName(), param.paramValue());
        }
        return Collections.unmodifiableMap(map);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper form = new BeanWrapperImpl(value);

        return isValidCore(form, context);
    }

    protected void addPropertyError(ConstraintValidatorContext context, String message,
            String propertyName) {
        context.disableDefaultConstraintViolation();
        ConstraintViolationBuilder constraintViolationBuilder =
                context.buildConstraintViolationWithTemplate(message);
        constraintViolationBuilder.addPropertyNode(propertyName);
        constraintViolationBuilder.addConstraintViolation();
    }

    protected void addPropertiesError(ConstraintValidatorContext context, String message,
            String... propertyNames) {
        context.disableDefaultConstraintViolation();
        ConstraintViolationBuilder constraintViolationBuilder =
                context.buildConstraintViolationWithTemplate(message);
        for (String propertyName : propertyNames) {
            constraintViolationBuilder.addPropertyNode(propertyName);
        }
        constraintViolationBuilder.addConstraintViolation();
    }

    protected void addBeanError(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        ConstraintViolationBuilder constraintViolationBuilder =
                context.buildConstraintViolationWithTemplate(message);
        constraintViolationBuilder.addBeanNode();
        constraintViolationBuilder.addConstraintViolation();
    }

    protected abstract boolean isValidCore(BeanWrapper form, ConstraintValidatorContext context);
}
