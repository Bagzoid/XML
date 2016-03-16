package com.kudlaienko.parser.engine.xml.parser.enums;

public enum TextSpecialCharacter {
    LT("<", "&lt;"), GT(">", "&gt;"), AMP("&", "&amp;"), APOS("'", "&apos;"), QUOTE("\"", "&quot;");

    private final String encodedValue;
    private final String codedValue;

    TextSpecialCharacter(String encodedValue, String codedValue) {
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
