package com.github.hondams.common.value;

import com.github.hondams.fw.value.CodeEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Gender implements CodeEnum {
    MALE("M"), //
    FEMALE("F"), //
    OTHER("O");

    private final String code;
}
