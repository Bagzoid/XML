package com.kudlaienko.parser.engine.xml.parser.token;

import com.kudlaienko.parser.engine.xml.property.Attribute;
import com.kudlaienko.parser.shell.service.Parser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AttributeTokenTest {
    private Parser<String, Attribute> attributeParser;

    @Before
    public void setUp() {
        attributeParser = XmlTokenFactory.getAttributeToken();
    }

    @Test
    public void testParse() throws Exception {
        String content = "value=\"1.0\"";
        Attribute attribute = attributeParser.parse(content).getValue();

        assertEquals("value", attribute.getName());
        assertEquals("1.0", attribute.getValue());
    }
}