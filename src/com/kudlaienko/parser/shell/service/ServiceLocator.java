package com.kudlaienko.parser.shell.service;

import java.util.Optional;

public interface ServiceLocator<T> {
    <S extends T> void addService(Class<S> classType, S value);

    <S extends T> Optional<S> getService(Class<S> classType);

    <S extends T> S ensureService(Class<S> classType);

}
