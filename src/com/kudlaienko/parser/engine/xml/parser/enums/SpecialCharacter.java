package com.kudlaienko.parser.engine.xml.parser.enums;

public enum SpecialCharacter {
    LT("<", "&lt;"), GT(">", "&gt;"), AMP("&", "&amp;");

    private final String encodedValue;
    private final String codedValue;

    SpecialCharacter(String encodedValue, String codedValue) {
        this.encodedValue = encodedValue;
        this.codedValue = codedValue;
    }

    public String getEncodedValue() {
        return encodedValue;
    }

    public String getCodedValue() {
        return codedValue;
    }

}
