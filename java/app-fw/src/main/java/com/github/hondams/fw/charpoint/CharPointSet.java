package com.github.hondams.fw.charpoint;

import java.util.function.IntPredicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CharPointSet {

    @Getter
    private final String charPointSetName;
    private final IntPredicate predicate;

    public boolean isValid(String value) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return value.codePoints().allMatch(this.predicate);
    }
}
