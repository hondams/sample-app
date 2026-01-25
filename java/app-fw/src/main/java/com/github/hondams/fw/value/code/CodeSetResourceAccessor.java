package com.github.hondams.fw.value.code;

import com.github.hondams.fw.util.ReloadableMultiLocaleResourceBundle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@RequiredArgsConstructor
public class CodeSetResourceAccessor {

    private final ReloadableMultiLocaleResourceBundle resourceBundle;

    public @NonNull CodeSetSourceType getSourceType(String codeSetName) {
        String key = CodeSetResourceKeys.getSourceTypeKey(codeSetName);
        String value = this.resourceBundle.getString(key);
        if (value == null) {
            throw new IllegalArgumentException(
                    "SourceType not found for codeSetName: " + codeSetName);
        }
        try {
            return CodeSetSourceType.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid SourceType value for codeSetName: "
                    + codeSetName + ", value: " + value, e);
        }
    }

    public List<String> getCodeSetNames() {
        Set<String> codeSetNameSet = new TreeSet<>();
        for (String key : this.resourceBundle.keySet()) {
            String codeSetName = CodeSetResourceKeys.getCodeSetName(key);
            if (codeSetName != null) {
                codeSetNameSet.add(codeSetName);
            }
        }
        return List.copyOf(codeSetNameSet);
    }

    public @NonNull String getDefaultLabel(@NonNull String codeSetName, @NonNull String code) {
        String key = CodeSetResourceKeys.getLabelKey(codeSetName, code);
        String label = this.resourceBundle.getString(key);
        if (label == null) {
            throw new IllegalArgumentException(
                    "Label not found for codeSetName: " + codeSetName + ", code: " + code);
        }
        return label;
    }

    public @NonNull String getLabel(@NonNull String codeSetName, @NonNull String code,
            @Nullable Locale locale) {
        String key = CodeSetResourceKeys.getLabelKey(codeSetName, code);
        String label = this.resourceBundle.getString(key, locale);
        if (label == null) {
            throw new IllegalArgumentException("Label not found for codeSetName: " + codeSetName
                    + ", code: " + code + ", locale: " + locale);
        }
        return label;
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
        return List.copyOf(values);
    }

    public void clearCache() {
        this.resourceBundle.clearCache();
    }
}
