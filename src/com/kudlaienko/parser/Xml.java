package com.kudlaienko.parser;

import com.kudlaienko.parser.engine.xml.parser.DocumentParser;
import com.kudlaienko.parser.engine.xml.property.Document;
import com.kudlaienko.parser.engine.xml.property.Tag;
import com.kudlaienko.parser.shell.exceptions.ParseException;
import com.kudlaienko.parser.shell.exceptions.ValidationException;
import com.kudlaienko.parser.shell.Validator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class Xml {
    private final DocumentParser documentParser;

    public Xml(DocumentParser documentParser) {
        this.documentParser = documentParser;
    }

    public Document parse(String content, Validator<Tag> validator) throws ParseException, ValidationException {
        Document document = documentParser.parse(content).getValue();
        if (validator != null) {
            validateEachTag(document.getMainTag(), validator);
        }
        return document;
    }

    public Document parse(String content) throws ParseException, ValidationException {
        Validator<Tag> validator = null;
        return parse(content, validator);
    }

    public Document parse(Path path) throws ParseException, ValidationException, IOException {
        Validator<Tag> tagValidator = null;
        return parse(path, tagValidator);
    }

    public Document parse(Path path, Validator<Tag> validator) throws ParseException, ValidationException, IOException {
        String content = Files.readAllLines(path).stream().collect(Collectors.joining());
        return parse(content, validator);
    }

    private void validateEachTag(Tag tag, Validator<Tag> validator) throws ValidationException {
        validator.validate(tag);
        for (Tag child : tag.getValues()) {
            validateEachTag(child, validator);
        }
    }

}

