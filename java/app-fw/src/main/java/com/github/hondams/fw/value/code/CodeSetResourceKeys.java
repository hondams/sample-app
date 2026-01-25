package com.github.hondams.fw.value.code;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CodeSetResourceKeys {

    // CodeSet.<CodeSetName>.SourceType=resource/database
    // CodeSet.<CodeSetName>.Format=[0-2:<OtherCodeSetName>]
    // CodeSet.<CodeSetName>.Value.<SortNumber>=<CodeValue>
    // CodeSet.<CodeSetName>.Label.<CodeValue>=<CodeLabel>

    public String getSourceTypeKey(String codeSetName) {
        return "CodeSet." + codeSetName + ".SourceType";
    }

    public String getFormatKey(String codeSetName, String code) {
        return "CodeSet." + codeSetName + ".Format." + code;
    }

    public String getValueKey(String codeSetName, int sortNumber) {
        return "CodeSet." + codeSetName + ".Value." + sortNumber;
    }

    public String getLabelKey(String codeSetName, String code) {
        return "CodeSet." + codeSetName + ".Label." + code;
    }
}
