package com.kudlaienko.parser.shell.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ValidationService {
    private Map<Class, Validator> validatorMap = new HashMap<>();

    public <T> void addService(Class<T> classType, Validator<T> value) {
        validatorMap.put(classType, value);
    }

    public <T> Optional<Validator<T>> getService(Class<T> classType) {
        return Optional.ofNullable(validatorMap.get(classType));
    }

    public <T> Validator<T> ensureService(Class<T> classType) {
        return getService(classType).orElseThrow(() -> new RuntimeException("Service not found: " + classType.getName()));
    }
}
