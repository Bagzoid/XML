package com.kudlaienko.parser.shell.service;

import com.kudlaienko.parser.engine.xml.property.Tag;
import com.kudlaienko.parser.engine.xml.property.TagBuilder;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ValidationServiceTest {

    @Test (expected = RuntimeException.class)
    public void testAddServiceWithoutExceptionFailed() throws Exception {
        TagBuilder tagBuilder = new TagBuilder();
        ValidationService validationService = new ValidationService();
        Validator<Tag> validator = validationService.ensureService(Tag.class);
    }

    @Test
    public void testAddServiceWithoutExceptionValid() throws Exception {
        Validator<String> validator = tag -> {};
        ValidationService validationService = new ValidationService();
        validationService.addService(String.class, validator);
        Validator<String> res = validationService.getService(String.class).get();

        assertNotNull(res);
    }
}