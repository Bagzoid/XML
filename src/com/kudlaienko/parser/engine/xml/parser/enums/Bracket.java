package com.kudlaienko.parser.engine.xml.parser.enums;

public enum Bracket {
    OPEN("<"), CLOSE(">"), AUTO_CLOSE("/>");

    private final String value;

    Bracket(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public boolean isClose() {
        return CLOSE.equals(this);
    }

    public boolean isAutoClose() {
        return AUTO_CLOSE.equals(this);
    }

    public static Bracket getEnum(String value) {
        for (Bracket bracket : Bracket.values()) {
            if (bracket.getValue().equals(value)) {
                return bracket;
            }
        }
        throw new IllegalArgumentException("Unknown Bracket value: " + value);
    }
}
