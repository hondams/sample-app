package com.github.hondams.fw.value.code;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;

@UtilityClass
public class CodeSetResourceKeys {

    // CodeSet.<CodeSetName>.SourceType=RESOURCE/DATABASE
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

    public String getCodeSetName(String key) {
        if (key == null || !key.startsWith("CodeSet.")) {
            return null;
        }
        String temp = Strings.CS.removeStart(key, "CodeSet.");
        return StringUtils.substringBefore(temp, ".");
    }

    public String getCodeValue(String labelKey) {
        if (labelKey == null || !labelKey.startsWith("CodeSet.")) {
            return null;
        }

        String temp = Strings.CS.removeStart(labelKey, "CodeSet.");
        temp = StringUtils.substringAfter(temp, ".");
        if (!temp.startsWith("Label.")) {
            return null;

        }
        return Strings.CS.removeStart(labelKey, "Label.");
    }

    public CodeSetResourceKeyType getKeyType(String key) {
        if (key == null || !key.startsWith("CodeSet.")) {
            return null;
        }

        String temp = Strings.CS.removeStart(key, "CodeSet.");
        temp = StringUtils.substringAfter(temp, ".");
        if (temp.startsWith("Label.")) {
            return CodeSetResourceKeyType.LABEL;
        } else if (temp.startsWith("Value.")) {
            return CodeSetResourceKeyType.VALUE;
        } else if (temp.startsWith("Format.")) {
            return CodeSetResourceKeyType.FORMAT;
        } else if ("SourceType".equals(temp)) {
            return CodeSetResourceKeyType.SOURCE_TYPE;
        }
        return null;
    }
}
