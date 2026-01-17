package com.github.hondams.fw.charpoint;

import jakarta.annotation.PostConstruct;
import java.util.function.IntPredicate;

public class CharPointSetRegistryInitializer {

    @PostConstruct
    public void initialize() {
        CharPointSetRegistry.register(CharPointSetNames.CONTROL, //
                CharPointUtils::isControl);
        CharPointSetRegistry.register(CharPointSetNames.HALF_WIDTH_WHITESPACE, //
                CharPointUtils::isHalfWidthWhitespace);
        CharPointSetRegistry.register(CharPointSetNames.HALF_WIDTH_ALPHABET_NUMBER, //
                any(CharPointUtils::isHalfWidthNumber, //
                        CharPointUtils::isHalfWidthUpperAlphabet, //
                        CharPointUtils::isHalfWidthLowerAlphabet));
        CharPointSetRegistry.register(CharPointSetNames.HALF_WIDTH_ALPHABET, //
                any(CharPointUtils::isHalfWidthUpperAlphabet, //
                        CharPointUtils::isHalfWidthLowerAlphabet));
        CharPointSetRegistry.register(CharPointSetNames.HALF_WIDTH_UPPER_ALPHABET, //
                CharPointUtils::isHalfWidthUpperAlphabet);
        CharPointSetRegistry.register(CharPointSetNames.HALF_WIDTH_LOWER_ALPHABET, //
                CharPointUtils::isHalfWidthLowerAlphabet);
        CharPointSetRegistry.register(CharPointSetNames.HALF_WIDTH_NUMBER, //
                CharPointUtils::isHalfWidthNumber);
        CharPointSetRegistry.register(CharPointSetNames.HALF_WIDTH_ASCII_SYMBOL, //
                CharPointUtils::isHalfWidthAsciiSymbol);
        CharPointSetRegistry.register(CharPointSetNames.HALF_WIDTH_KATAKANA, //
                CharPointUtils::isHalfWidthKatakana);
        CharPointSetRegistry.register(CharPointSetNames.HALF_WIDTH_ASCII, //
                CharPointUtils::isHalfWidthAscii);
        CharPointSetRegistry.register(CharPointSetNames.FULL_WIDTH_ALPHABET_NUMBER, //
                any(CharPointUtils::isFullWidthNumber, //
                        CharPointUtils::isFullWidthUpperAlphabet, //
                        CharPointUtils::isFullWidthLowerAlphabet));
        CharPointSetRegistry.register(CharPointSetNames.FULL_WIDTH_ALPHABET, //
                any(CharPointUtils::isFullWidthUpperAlphabet, //
                        CharPointUtils::isFullWidthLowerAlphabet));
        CharPointSetRegistry.register(CharPointSetNames.FULL_WIDTH_UPPER_ALPHABET, //
                CharPointUtils::isFullWidthUpperAlphabet);
        CharPointSetRegistry.register(CharPointSetNames.FULL_WIDTH_LOWER_ALPHABET, //
                CharPointUtils::isFullWidthLowerAlphabet);
        CharPointSetRegistry.register(CharPointSetNames.FULL_WIDTH_NUMBER, //
                CharPointUtils::isFullWidthNumber);
        CharPointSetRegistry.register(CharPointSetNames.FULL_WIDTH_HIRAGANA, //
                CharPointUtils::isFullWidthHiragana);
        CharPointSetRegistry.register(CharPointSetNames.FULL_WIDTH_KATAKANA, //
                CharPointUtils::isFullWidthKatakana);
        CharPointSetRegistry.register(CharPointSetNames.FULL_WIDTH_ASCII_SYMBOL, //
                CharPointUtils::isFullWidthAsciiSymbol);
        CharPointSetRegistry.register(CharPointSetNames.FULL_WIDTH_ASCII, //
                CharPointUtils::isFullWidthAscii);
        CharPointSetRegistry.register(CharPointSetNames.FULL_WIDTH_WHITESPACE, //
                CharPointUtils::isFullWidthWhitespace);
        CharPointSetRegistry.register(CharPointSetNames.WHITESPACE, //
                any(CharPointUtils::isHalfWidthWhitespace, //
                        CharPointUtils::isFullWidthWhitespace));
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    protected IntPredicate any(IntPredicate... predicates) {
        return codePoint -> {
            for (int i = 0; i < predicates.length; i++) {
                if (predicates[i].test(codePoint)) {
                    return true;
                }
            }
            return false;
        };
    }
}
