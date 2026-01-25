package com.github.hondams.fw.value.code;

import com.github.hondams.fw.util.ReloadableMultiLocaleResourceBundle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@RequiredArgsConstructor
public class CodeSetResourceAccessor {

    private final ReloadableMultiLocaleResourceBundle resourceBundle;

    public @NonNull String getDefaultLabel(@NonNull String codeSetName, @NonNull String code) {
        String key = CodeSetResourceKeys.getLabelKey(codeSetName, code);
        return this.resourceBundle.getString(key);
    }

    public @NonNull String getLabel(@NonNull String codeSetName, @NonNull String code,
            @Nullable Locale locale) {
        String key = CodeSetResourceKeys.getLabelKey(codeSetName, code);
        return this.resourceBundle.getString(key, locale);
    }

    public @NonNull List<String> getValues(@NonNull String codeSetName) {
        List<String> values = new ArrayList<>();
        int index = 1;
        String key = CodeSetResourceKeys.getValueKey(codeSetName, index++);
        String value = this.resourceBundle.getString(key);
        while (value != null) {
            values.add(value);
            key = CodeSetResourceKeys.getValueKey(codeSetName, index++);
            value = this.resourceBundle.getString(key);
        }
        return values;
    }
}
