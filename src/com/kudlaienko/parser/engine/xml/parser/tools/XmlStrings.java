package com.kudlaienko.parser.engine.xml.parser.tools;

import com.kudlaienko.parser.engine.xml.parser.enums.TextSpecialCharacter;

public class XmlStrings {
    public static String encodeSpecialValues(String content) {
        String result = content;
        for (TextSpecialCharacter specialCharacter : TextSpecialCharacter.values()) {
            result = result.replaceAll(specialCharacter.getCodedValue(), specialCharacter.getEncodedValue());
        }

        return result;
    }
}
