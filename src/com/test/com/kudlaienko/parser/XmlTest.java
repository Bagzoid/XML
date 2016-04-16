package com.kudlaienko.parser;

import com.kudlaienko.parser.engine.xml.ParserFactoryImpl;
import com.kudlaienko.parser.engine.xml.property.Document;
import com.kudlaienko.parser.engine.xml.property.Tag;
import com.kudlaienko.parser.shell.exceptions.ParseException;
import com.kudlaienko.parser.shell.exceptions.ValidationException;
import com.kudlaienko.parser.shell.CompoundValidator;
import com.kudlaienko.parser.shell.Validator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class XmlTest {
    private Xml xml;

    @Before
    public void setUp() {
        xml = new Xml(new ParserFactoryImpl().getDocumentParser());
    }

    @Test
    public void parseTestValid() throws ParseException, ValidationException {
        String content = "<?xml version=\"1.1\" encoding=\"UTF-8\" ?>\n\n" +
                "<main>\n" +
                "<objects>\n" +
                "<object>\n" +
                "<id>\n" +
                "12000A" +
                "</id>\n" +
                "</object>\n" +
                "</objects>\n" +
                "</main>\n";

        Document document = xml.parse(content, null);
        assertEquals("main", document.getMainTag().getName());
    }

    @Test(expected = ValidationException.class)
    public void parseValidationTestFailed() throws ParseException, ValidationException {
        String content = "<?xml version=\"1.1\" encoding=\"UTF-8\" ?>\n\n" +
                "<main>\n" +
                "<objects>\n" +
                "<object>\n" +
                "<id>\n" +
                "12000A" +
                "</id>\n" +
                "</object>\n" +
                "</objects>\n" +
                "</main>\n";

        Document document = xml.parse(content, v -> {
            if (v.getName().equals("id")) {
                try {
                    Integer.valueOf(v.getValue());
                } catch (NumberFormatException e) {
                    throw new ValidationException(e.getMessage());
                }
            }
        });
        assertEquals("main", document.getMainTag().getName());
    }

    @Test(expected = ValidationException.class)
    public void parseTestCompundIdFailed() throws ParseException, ValidationException {
        String content = "<?xml version=\"1.1\" encoding=\"UTF-8\" ?>\n\n" +
                "<main>\n" +
                "<objects>\n" +
                "<object>\n" +
                "<id>\n" +
                "12000A" +
                "</id>\n" +
                "</object>\n" +
                "</objects>\n" +
                "</main>\n";

        Validator<Tag> idValidator = tag -> {
            try {
                Integer.valueOf(tag.getValue());
            } catch (NumberFormatException e) {
                throw new ValidationException(e.getMessage());
            }
        };
        Validator<Tag> objectValidator = tag -> {
            if (tag.getName().equals("objects") && tag.getValues().isEmpty()) {
                throw new ValidationException("Objects list can't be empty");
            }
        };
        CompoundValidator<Tag> compoundValidator = new CompoundValidator<>(idValidator, objectValidator);
        Document document = xml.parse(content, compoundValidator);
        assertEquals("main", document.getMainTag().getName());
    }

    @Test(expected = ValidationException.class)
    public void parseTestCompoundObjectsFailed() throws ParseException, ValidationException {
        String content = "<?xml version=\"1.1\" encoding=\"UTF-8\" ?>\n\n" +
                "<main>\n" +
                "<objects>\n" +
                "</objects>\n" +
                "</main>\n";

        Validator<Tag> idValidator = tag -> {
            try {
                Integer.valueOf(tag.getValue());
            } catch (NumberFormatException e) {
                throw new ValidationException(e.getMessage());
            }
        };
        Validator<Tag> objectValidator = tag -> {
            if (tag.getName().equals("objects") && tag.getValues().isEmpty()) {
                throw new ValidationException("Objects list can't be empty");
            }
        };
        CompoundValidator<Tag> compoundValidator = new CompoundValidator<>(idValidator, objectValidator);
        Document document = xml.parse(content, compoundValidator);
        assertEquals("main", document.getMainTag().getName());
    }


}