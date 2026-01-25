package com.github.hondams.fw.value.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import org.jspecify.annotations.NonNull;

public final class ResourceCodeSet implements CodeSet {

    @Getter
    private final String codeSetName;
    @Getter
    private final List<Code> codes;

    public ResourceCodeSet(@NonNull CodeSetResourceAccessor resourceAccessor,
            @NonNull String codeSetName) {
        Objects.requireNonNull(resourceAccessor, "resourceAccessor must not be null");
        this.codeSetName = Objects.requireNonNull(codeSetName, "codeSetName must not be null");
        this.codes = Collections.unmodifiableList(createCodes(resourceAccessor, codeSetName));
    }

    private List<Code> createCodes(CodeSetResourceAccessor resourceAccessor, String codeSetName) {
        List<String> values = resourceAccessor.getValues(codeSetName);
        List<Code> codes = new ArrayList<>(values.size());
        for (String value : values) {
            codes.add(new ResourceCode(resourceAccessor, codeSetName, value));
        }
        return codes;
    }
}
