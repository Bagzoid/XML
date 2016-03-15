package com.kudlaienko.parser.engine.xml.parser.tokens;

import com.kudlaienko.parser.engine.xml.ParserDecorator;
import com.kudlaienko.parser.engine.xml.property.Attribute;
import com.kudlaienko.parser.shell.Parser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AttributeParserTest {
    private Parser<String, Attribute> attributeParser;

    @Before
    public void setUp() {
        attributeParser = ParserDecorator.getAttributeParser();
    }

    @Test
    public void testParse() throws Exception {
        String content = "value=\"1.0\"";
        Attribute attribute = attributeParser.parse(content).getValue();

        assertEquals("value", attribute.getName());
        assertEquals("1.0", attribute.getValue());
    }
}