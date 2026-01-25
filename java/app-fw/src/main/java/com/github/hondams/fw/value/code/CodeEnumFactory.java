package com.github.hondams.fw.value.code;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CodeEnumFactory {

    private final Map<Class<?>, Map<String, Object>> CACHE = new ConcurrentHashMap<>();

    public <T> T fromCode(Class<T> enumClass, String code) {
        Map<String, Object> codeMap =
                CACHE.computeIfAbsent(enumClass, CodeEnumFactory::createCodeMap);
        @SuppressWarnings("unchecked")
        T result = (T) codeMap.get(code);
        if (result != null) {
            return result;
        }
        throw new IllegalArgumentException(
                "No enum constant for code: " + code + " in " + enumClass.getName());
    }

    private Map<String, Object> createCodeMap(Class<?> enumClass) {
        Map<String, Object> map = new HashMap<>();
        for (Object enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant instanceof CodeEnum codeEnum) {
                map.put(codeEnum.getCode(), enumConstant);
            }
        }
        return map;
    }
}
