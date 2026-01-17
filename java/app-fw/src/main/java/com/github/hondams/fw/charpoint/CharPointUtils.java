package com.github.hondams.fw.charpoint;


import lombok.experimental.UtilityClass;

@UtilityClass
public class CharPointUtils {

    public boolean isControl(int codePoint) {
        return codePoint < 0x20 // 0x20:space
                || codePoint == 0x7F; // 0x7F:delete
    }

    public boolean isHalfWidthUpperAlphabet(int codePoint) {
        return 'A' <= codePoint && codePoint <= 'Z';
    }

    public boolean isHalfWidthLowerAlphabet(int codePoint) {
        return 'a' <= codePoint && codePoint <= 'z';
    }

    public boolean isHalfWidthNumber(int codePoint) {
        return '0' <= codePoint && codePoint <= '9';
    }

    public boolean isHalfWidthAsciiSymbol(int codePoint) {
        return (0x21 <= codePoint // 0x21:exclamation mark
                && codePoint <= 0x2F) // 0x2F:slash
                || (0x3A <= codePoint // 0x3A:colon
                        && codePoint <= 0x40) // 0x40:commercial at
                || (0x5B <= codePoint // 0x5B:left square bracket
                        && codePoint <= 0x60) // 0x60:grave accent
                || (0x7B <= codePoint // 0x7B:left curly bracket
                        && codePoint <= 0x7E); // 0x7E:tilde
    }

    public boolean isHalfWidthKatakana(int codePoint) {
        return 0xFF66 <= codePoint // 0xFF66:HALFWIDTH KATAKANA LETTER WO
                && codePoint <= 0xFF9F;// 0xFF9F:HALFWIDTH KATAKANA SEMI-VOICED SOUND MARK
    }

    public boolean isHalfWidthAscii(int codePoint) {
        return 0x21 <= codePoint // 0x21:exclamation mark
                && codePoint <= 0x7E; // 0x7E:tilde
    }

    public boolean isFullWidthUpperAlphabet(int codePoint) {
        return 'Ａ' <= codePoint && codePoint <= 'Ｚ';
    }

    public boolean isFullWidthLowerAlphabet(int codePoint) {
        return 'ａ' <= codePoint && codePoint <= 'ｚ';
    }

    public boolean isFullWidthNumber(int codePoint) {
        return '０' <= codePoint && codePoint <= '９';
    }

    public boolean isFullWidthHiragana(int codePoint) {
        return 0x3040 <= codePoint && codePoint <= 0x309F;// Hiragana:U+3040..U+309F
    }

    public boolean isFullWidthKatakana(int codePoint) {
        return (0x30A0 <= codePoint && codePoint <= 0x30FF)// Katakana:U+30A0..U+30FF
                || (0x31F0 <= codePoint && codePoint <= 0x31FF); // Katakana Phonetic
                                                                 // Extensions:U+31F0..U+31FF;
    }

    public boolean isFullWidthAsciiSymbol(int codePoint) {
        return (0xFF01 <= codePoint // 0xFF01:fullwidth exclamation mark
                && codePoint <= 0xFF0F) // 0xFF0F:fullwidth solidus
                || (0xFF1A <= codePoint // 0xFF1A:fullwidth colon
                        && codePoint <= 0xFF20) // 0xFF20:fullwidth commercial at
                || (0xFF3B <= codePoint // 0xFF3B:fullwidth left square bracket
                        && codePoint <= 0xFF40) // 0xFF40:fullwidth grave accent
                || (0xFF5B <= codePoint // 0xFF5B:fullwidth left curly bracket
                        && codePoint <= 0xFF5E); // 0xFF5E:fullwidth tilde
    }

    public boolean isFullWidthAscii(int codePoint) {
        return 0xFF01 <= codePoint // 0xFF01:fullwidth exclamation mark
                && codePoint <= 0xFF5E; // 0xFF5E:fullwidth tilde
    }

    public boolean isHalfWidthWhitespace(int codePoint) {
        return codePoint == 0x20 // 0x20:space
                || codePoint == '\t' // 0x09:horizontal tab
                || codePoint == '\n' // 0x0A:line feed
                || codePoint == '\r' // 0x0D:carriage return
                || codePoint == '\f'; // 0x0C:form feed
    }

    public boolean isFullWidthWhitespace(int codePoint) {
        return codePoint == 0x3000; // 0x3000:IDEOGRAPHIC SPACE
    }
}
