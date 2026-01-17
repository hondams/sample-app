package com.github.hondams.fw.charpoint;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntPredicate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CharPointSetRegistry {

    private final Map<String, CharPointSet> charPointSetMap = new ConcurrentHashMap<>();

    public CharPointSet getCharPointSet(String charPointSetName) {
        CharPointSet charPointSet = charPointSetMap.get(charPointSetName);
        if (charPointSet == null) {
            throw new IllegalArgumentException("Unknown CharPointSet name: " + charPointSetName);
        }
        return charPointSet;
    }

    public void register(String charPointSetName, IntPredicate predicate) {
        charPointSetMap.put(charPointSetName, new CharPointSet(charPointSetName, predicate));
    }
}
