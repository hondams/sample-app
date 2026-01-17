package com.github.hondams.test.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.springframework.boot.validation.MessageInterpolatorFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.ClassUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@UtilityClass
public class ValidationTestUtils {

    private String messageBasenames = "messages";
    private String messageDefaultEncoding = "UTF-8";

    private MessageSource messageSource;

    private LocalValidatorFactoryBean validator;

    public void setMessageBasenames(String messageBasenames) {
        if (!Objects.equals(ValidationTestUtils.messageBasenames, messageBasenames)) {
            ValidationTestUtils.messageBasenames = messageBasenames;
            ValidationTestUtils.messageSource = null;
            ValidationTestUtils.validator = null;
        }
    }

    private MessageSource getMessageSource() {
        if (messageSource == null) {
            // org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration
            ResourceBundleMessageSource resourceBundleMessageSource =
                    new ResourceBundleMessageSource();
            resourceBundleMessageSource.setBasename(messageBasenames);
            resourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
            resourceBundleMessageSource.setDefaultEncoding(messageDefaultEncoding);
            messageSource = resourceBundleMessageSource;
        }
        return messageSource;
    }

    private LocalValidatorFactoryBean getValidator() {
        if (validator == null) {
            // org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration
            LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
            MessageInterpolatorFactory interpolatorFactory =
                    new MessageInterpolatorFactory(getMessageSource());
            validatorFactoryBean.setMessageInterpolator(interpolatorFactory.getObject());
            validatorFactoryBean.afterPropertiesSet();
            validator = validatorFactoryBean;
        }
        return validator;
    }

    public BindingResult validate(Object form) {
        BindingResult bindingResult = createBindingResult(form);
        getValidator().validate(form, bindingResult);
        return bindingResult;
    }

    public BindingResult validate(Object form, Class<?>... groups) {
        BindingResult bindingResult = createBindingResult(form);
        getValidator().validate(form, bindingResult, (Object[]) groups);
        return bindingResult;
    }

    public List<String> getValidatedMessages(Object form, Locale locale) {
        BindingResult bindingResult = validate(form);
        return getMessages(bindingResult.getAllErrors(), locale);
    }

    public List<String> getValidatedMessages(Object form, Locale locale, Class<?>... groups) {
        BindingResult bindingResult = validate(form, groups);
        return getMessages(bindingResult.getAllErrors(), locale);
    }

    private BindingResult createBindingResult(Object form) {
        return new BeanPropertyBindingResult(form,
                ClassUtils.getShortNameAsProperty(form.getClass()));
    }

    private List<String> getMessages(List<ObjectError> errors, Locale locale) {
        List<String> messages = new ArrayList<>();
        for (ObjectError error : errors) {
            messages.add(getMessageSource().getMessage(error, locale));
        }
        return messages;
    }
}
