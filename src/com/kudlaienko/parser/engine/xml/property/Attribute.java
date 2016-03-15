package com.kudlaienko.parser.engine.xml.property;

import com.kudlaienko.parser.shell.interfaces.NodeName;
import com.kudlaienko.parser.shell.interfaces.NodeValue;

public class Attribute implements NodeName, NodeValue<String> {
    final private String name;
    final private String value;

    public Attribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getName() {
        return name;
    }
}
