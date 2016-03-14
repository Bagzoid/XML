package com.kudlaienko.parser.engine.xml.property;


public class Document {

    private String header;
    private Tag mainTag;

    public Document(String header, Tag mainTag) {
        this.header = header;
        this.mainTag = mainTag;
    }

    public String getHeader() {
        return header;
    }

    public Tag getMainTag() {
        return mainTag;
    }

    @Override
    public String toString() {
        return "Document{\n" +
                "header='" + header + '\'' +
                ",\nmainTag=" + mainTag +
                "\n}";
    }
}
