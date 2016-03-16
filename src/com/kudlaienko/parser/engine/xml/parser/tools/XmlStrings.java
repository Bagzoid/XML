package com.kudlaienko.parser.engine.xml.parser.tools;

import com.kudlaienko.parser.engine.xml.parser.enums.TextSpecialCharacter;

public class XmlStrings {
    public static String encodeSpecialValues(String content) {
        return encodeSpecialValuesBuilder(content);
    }

    private static String encodeSpecialValuesBuilder(String content) {
        StringBuilder result = new StringBuilder(content);
        int start;
        int findNext;
        for (TextSpecialCharacter specialCharacter : TextSpecialCharacter.values()) {
            findNext = 0;
            start = result.indexOf(specialCharacter.getCodedValue(), findNext);
            while (start >= 0) {
                result.replace(start, start + specialCharacter.getCodedValue().length(), specialCharacter.getEncodedValue());
                findNext = start + specialCharacter.getEncodedValue().length();
                start = result.indexOf(specialCharacter.getCodedValue(), findNext);
            }
        }

        return result.toString();
    }

    private static String encodeSpecialValuesRegexp(String content) {
        String result = content;
        for (TextSpecialCharacter specialCharacter : TextSpecialCharacter.values()) {
            result = result.replaceAll(specialCharacter.getCodedValue(), specialCharacter.getEncodedValue());
        }

        return result;
    }
}
