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
    public void testParseValid() throws Exception {
        String content = "value=\"1.0\"";
        Attribute attribute = attributeParser.parse(content).getValue();

        assertEquals("value", attribute.getName());
        assertEquals("1.0", attribute.getValue());
    }

    @Test
    public void testParseWithSpecialCharsValid() throws Exception {
        String content = "value=\"&gt;da&amp;ta&lt;\"";
        Attribute attribute = attributeParser.parse(content).getValue();

        assertEquals("value", attribute.getName());
        assertEquals(">da&ta<", attribute.getValue());
    }

    @Test
    public void testParseWithSpecialCharsQuoteValid() throws Exception {
        String content = "value=\"&apos;a&apos;==&quot;b&quot;\"";
        Attribute attribute = attributeParser.parse(content).getValue();

        assertEquals("value", attribute.getName());
        assertEquals("'a'==\"b\"", attribute.getValue());
    }
}