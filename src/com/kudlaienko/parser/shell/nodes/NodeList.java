package com.kudlaienko.parser.shell.nodes;


import java.util.List;

public interface NodeList<V> extends CustomName {

    List<V> getValues();

}
