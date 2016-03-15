package com.kudlaienko.parser.shell;

public class ParseResult<V> {
    final V value;
    final int parserPos;

    public ParseResult(V value, int parserPos) {
        this.value = value;
        this.parserPos = parserPos;
    }

    public V getValue() {
        return value;
    }

    public int getParserPos() {
        return parserPos;
    }
}
