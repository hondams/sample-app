package com.github.hondams.fw.value.code;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CodeEnumUtils {

    private final Map<Class<?>, Map<String, Object>> codesMap = new ConcurrentHashMap<>();

    private final Map<Class<?>, String> codeSetNameMap = new ConcurrentHashMap<>();

    public <T extends CodeEnum> T fromCode(Class<T> enumClass, String code) {
        Map<String, Object> codeMap =
                codesMap.computeIfAbsent(enumClass, CodeEnumUtils::createCodeMap);
        @SuppressWarnings("unchecked")
        T result = (T) codeMap.get(code);
        if (result != null) {
            return result;
        }
        throw new IllegalArgumentException(
                "No enum constant for code: " + code + " in " + enumClass.getName());
    }

    public <T extends CodeEnum> String getCodeSetName(Class<T> enumClass) {
        return codeSetNameMap.computeIfAbsent(enumClass, CodeEnumUtils::extractCodeSetName);
    }

    private String extractCodeSetName(Class<?> enumClass) {
        CodeSetName codeSetName = enumClass.getAnnotation(CodeSetName.class);
        if (codeSetName == null) {
            throw new IllegalArgumentException(
                    "CodeSetName annotation not found on enum class: " + enumClass.getName());
        }
        return codeSetName.value();
    }

    private Map<String, Object> createCodeMap(Class<?> enumClass) {
        Map<String, Object> map = new HashMap<>();
        for (Object enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant instanceof CodeEnum codeEnum) {
                map.put(codeEnum.getCode(), enumConstant);
            }
        }
        return Map.copyOf(map);
    }
}
