package com.github.hondams.fw.value.code;

import java.util.Locale;
import org.jspecify.annotations.NonNull;

public interface Code {

    @NonNull String getCode();

    @NonNull String getDefaultLabel();

    @NonNull String getLabel(Locale locale);
}
