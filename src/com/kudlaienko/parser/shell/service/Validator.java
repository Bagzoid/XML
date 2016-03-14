package com.kudlaienko.parser.shell.service;

import com.kudlaienko.parser.shell.exceptions.ValidationException;

public interface Validator<T> {

    void validate(T t) throws ValidationException;

}
