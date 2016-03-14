package com.kudlaienko.parser.engine.xml.parser.token;

import com.kudlaienko.parser.engine.xml.property.Attribute;
import com.kudlaienko.parser.shell.service.ParseResult;
import com.kudlaienko.parser.shell.service.Parser;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AttributeListTokenTest {
    private Parser<String, List<Attribute>> listParser;

    @Before
    public void setUp() {
        listParser = XmlTokenFactory.getAttributeListToken();
    }

    @Test
    public void testParseAttributesValid() throws Exception {
        String content = "a=\"1.0\" b=\"2.0\"";
        ParseResult<List<Attribute>> parseResult = listParser.parse(content);
        List<Attribute> attributeList = parseResult.getValue();

        assertEquals(content.length(), parseResult.getParserPos());
        assertEquals(2, attributeList.size());
        assertEquals("a", attributeList.get(0).getName());
        assertEquals("1.0", attributeList.get(0).getValue());
        assertEquals("b", attributeList.get(1).getName());
        assertEquals("2.0", attributeList.get(1).getValue());
    }


    @Test
    public void testParseOnlyFirstAttributeValid() throws Exception {
        String content = "a=\"1.0\" b=\"2.0";
        ParseResult<List<Attribute>> parseResult = listParser.parse(content);
        List<Attribute> attributeList = parseResult.getValue();

        assertEquals(content.indexOf("b="), parseResult.getParserPos());
        assertEquals(1, attributeList.size());
        assertEquals("a", attributeList.get(0).getName());
        assertEquals("1.0", attributeList.get(0).getValue());
    }
}