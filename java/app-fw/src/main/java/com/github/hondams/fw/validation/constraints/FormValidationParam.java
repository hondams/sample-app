package com.github.hondams.fw.validation.constraints;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FormValidationParam {

    String paramName();

    String paramValue();
}
