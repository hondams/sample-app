package com.github.hondams.fw.value.code;

import java.util.Locale;

public interface Code {

    String getCode();

    String getDefaultLabel();

    String getLabel(Locale locale);
}
