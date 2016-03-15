package com.kudlaienko.parser.shell;

import com.kudlaienko.parser.shell.exceptions.ValidationException;

public interface Validator<T> {

    void validate(T t) throws ValidationException;

}
