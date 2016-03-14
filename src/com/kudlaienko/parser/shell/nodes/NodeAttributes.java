package com.kudlaienko.parser.shell.nodes;


import java.util.List;

public interface NodeAttributes<V> extends CustomName {

    List<V> getAttributes();

}
