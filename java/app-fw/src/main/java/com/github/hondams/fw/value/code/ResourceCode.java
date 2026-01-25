package com.github.hondams.fw.value.code;

import java.util.Locale;
import java.util.Objects;
import lombok.Getter;
import org.jspecify.annotations.NonNull;

public final class ResourceCode implements Code {

    private final CodeSetResourceAccessor resourceAccessor;
    private final String codeSetName;
    @Getter
    private final String code;

    public ResourceCode(@NonNull CodeSetResourceAccessor resourceAccessor,
            @NonNull String codeSetName, String code) {
        this.resourceAccessor =
                Objects.requireNonNull(resourceAccessor, "resourceAccessor must not be null");
        this.codeSetName = Objects.requireNonNull(codeSetName, "codeSetName must not be null");
        this.code = Objects.requireNonNull(code, "code must not be null");
    }

    @Override
    public @NonNull String getDefaultLabel() {
        return this.resourceAccessor.getDefaultLabel(this.codeSetName, this.code);
    }

    @Override
    public @NonNull String getLabel(Locale locale) {
        if (locale == null) {
            return getDefaultLabel();
        }
        return this.resourceAccessor.getLabel(this.codeSetName, this.code, locale);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResourceCode other)) {
            return false;
        }
        return this.codeSetName.equals(other.codeSetName) && this.code.equals(other.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codeSetName, this.code);
    }

    @Override
    public String toString() {
        return this.codeSetName + ":" + this.code;
    }
}
