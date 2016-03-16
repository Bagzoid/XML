package com.kudlaienko.parser.engine.xml.parser.tools;

import com.kudlaienko.parser.engine.xml.parser.enums.SpecialCharacter;

public class XmlStrings {
    public static String encodeSpecialValues(String content) {
        String result = content;
        for (SpecialCharacter specialCharacter : SpecialCharacter.values()) {
            result = result.replaceAll(specialCharacter.getCodedValue(), specialCharacter.getEncodedValue());
        }

        return result;
    }
}
