package com.kudlaienko.parser.shell.service;

import java.util.*;

public final class ParserService implements ServiceLocator<Parser> {
    private Map<Class, Parser> parserMap = new HashMap();

    public void addService(List<Parser> parserLisrt) {
        for (Parser parser : parserLisrt) {
            parserMap.put(parser.getClass(), parser);
        }
    }

    @Override
    public <S extends Parser> void addService(Class<S> classType, S value) {
        parserMap.put(classType, value);
    }

    @Override
    public <S extends Parser> Optional<S> getService(Class<S> classType) {
        return Optional.ofNullable((S) parserMap.get(classType));
    }

    @Override
    public <S extends Parser> S ensureService(Class<S> classType) {
        Parser parser = parserMap.get(classType);
        Objects.requireNonNull(parser, "Required service not found: " + classType.getName());
        return (S) parser;
    }

}
