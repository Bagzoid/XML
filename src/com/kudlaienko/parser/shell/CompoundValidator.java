package com.kudlaienko.parser.shell;

import com.kudlaienko.parser.shell.exceptions.ValidationException;

import java.util.Arrays;
import java.util.List;

public class CompoundValidator<T> implements Validator<T> {
    List<Validator<T>> validators;

    public CompoundValidator(Validator<T>... validators) {
        this.validators = Arrays.asList(validators);
    }

    @Override
    public void validate(T t) throws ValidationException {
        for (Validator<T> validator : validators) {
            validator.validate(t);
        }
    }

}
