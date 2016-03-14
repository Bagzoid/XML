package com.kudlaienko.parser.engine.xml.property;

import com.kudlaienko.parser.shell.nodes.NodeNamedValue;

public class Attribute implements NodeNamedValue<String> {
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
