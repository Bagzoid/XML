package com.kudlaienko.parser.engine.xml.property;


import com.kudlaienko.parser.shell.interfaces.NodeAttributes;
import com.kudlaienko.parser.shell.interfaces.NodeList;
import com.kudlaienko.parser.shell.interfaces.NodeName;
import com.kudlaienko.parser.shell.interfaces.NodeValue;

import java.util.Collections;
import java.util.List;

public class Tag implements NodeName, NodeValue<String>, NodeList<Tag>, NodeAttributes<Attribute> {

    private final String name;
    private final String value;
    private final List<Tag> tags;
    private final List<Attribute> attributes;

    public Tag(String name, String value, List<Attribute> attributes, List<Tag> tags) {
        this.name = name;
        this.value = value;

        if (tags != null) {
            this.tags = Collections.unmodifiableList(tags);
        } else {
            this.tags = Collections.emptyList();
        }
        if (attributes != null) {
            this.attributes = Collections.unmodifiableList(attributes);
        } else {
            this.attributes = Collections.emptyList();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public List<Attribute> getAttributes() {
        return attributes;
    }

    @Override
    public List<Tag> getValues() {
        return tags;
    }

    @Override
    public String toString() {
        return "\nTag{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", attributes=" + attributes +
                ", child tags=" + tags +
                '}';
    }
}
