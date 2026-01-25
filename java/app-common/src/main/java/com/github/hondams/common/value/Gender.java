package com.github.hondams.common.value;

import com.github.hondams.fw.value.code.CodeEnum;
import com.github.hondams.fw.value.code.CodeSetName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@CodeSetName("Gender")
public enum Gender implements CodeEnum {
    MALE("M"), //
    FEMALE("F"), //
    OTHER("O");

    private final String code;
}
