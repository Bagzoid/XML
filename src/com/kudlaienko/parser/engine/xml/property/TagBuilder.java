package com.kudlaienko.parser.engine.xml.property;

import java.util.List;

public class TagBuilder {
    private String name;
    private String value;
    private List<Attribute> attributes;
    private List<Tag> tags;

    public TagBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public TagBuilder setValue(String value) {
        this.value = value;
        return this;
    }

    public TagBuilder setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
        return this;
    }

    public TagBuilder setTags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Tag createTag() {
        return new Tag(name, value, attributes, tags);
    }
}