package com.kudlaienko.parser.engine.xml.parser.tokens;

import com.kudlaienko.parser.engine.xml.ParserFactoryImpl;
import com.kudlaienko.parser.engine.xml.property.Tag;
import com.kudlaienko.parser.shell.exceptions.ParseException;
import com.kudlaienko.parser.shell.ParseResult;
import com.kudlaienko.parser.shell.Parser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class TagParserTest {

    private Parser<String, Tag> tagFullToken;

    @Before
    public void setUp() {
        tagFullToken = new ParserFactoryImpl().getTagParser();
    }

    @Test
    public void testParseSimpleValid() throws Exception {
        String content = "<abc>data</abc>";
        ParseResult<Tag> parseResult = tagFullToken.parse(content);
        Tag tag = parseResult.getValue();
        assertEquals("abc", tag.getName());
        assertEquals("data", tag.getValue());
        assertTrue(tag.getAttributes().isEmpty());
        assertTrue(tag.getValues().isEmpty());
        assertEquals(content.length(), parseResult.getParserPos());
    }

    @Test
    public void testParseOneSpecialCharacterValid() throws Exception {
        String content = "<abc>data&lt;</abc>";
        ParseResult<Tag> parseResult = tagFullToken.parse(content);
        Tag tag = parseResult.getValue();
        assertEquals("abc", tag.getName());
        assertEquals("data<", tag.getValue());
        assertTrue(tag.getAttributes().isEmpty());
        assertTrue(tag.getValues().isEmpty());
        assertEquals(content.length(), parseResult.getParserPos());
    }

    @Test
    public void testParseMultiSpecialCharactersValid() throws Exception {
        String content = "<abc>&gt;da&amp;ta&lt;</abc>";
        ParseResult<Tag> parseResult = tagFullToken.parse(content);
        Tag tag = parseResult.getValue();
        assertEquals("abc", tag.getName());
        assertEquals(">da&ta<", tag.getValue());
        assertTrue(tag.getAttributes().isEmpty());
        assertTrue(tag.getValues().isEmpty());
        assertEquals(content.length(), parseResult.getParserPos());
    }

    @Test
    public void testParseMultiSpecialCharactersWithCDATAValid() throws Exception {
        String content = "<abc>&gt;da&amp;ta&lt; - <![CDATA[&gt;da&amp;ta&lt;]]></abc>";
        ParseResult<Tag> parseResult = tagFullToken.parse(content);
        Tag tag = parseResult.getValue();
        assertEquals("abc", tag.getName());
        assertEquals(">da&ta< - &gt;da&amp;ta&lt;", tag.getValue());
        assertTrue(tag.getAttributes().isEmpty());
        assertTrue(tag.getValues().isEmpty());
        assertEquals(content.length(), parseResult.getParserPos());
    }

    @Test
    public void testParseWithCommentValid() throws Exception {
        String content = "<abc><!--comment before-->da<!--comment inside-->ta<!--comment after--></abc>";
        ParseResult<Tag> parseResult = tagFullToken.parse(content);
        Tag tag = parseResult.getValue();
        assertEquals("abc", tag.getName());
        assertEquals("data", tag.getValue());
        assertTrue(tag.getAttributes().isEmpty());
        assertTrue(tag.getValues().isEmpty());
        assertEquals(content.length(), parseResult.getParserPos());
    }

    @Test
    public void testParseCDataValid() throws Exception {
        String content = "<abc><![CDATA[<sender>John Smith</sender>]]></abc>";
        ParseResult<Tag> parseResult = tagFullToken.parse(content);
        Tag tag = parseResult.getValue();
        assertEquals("abc", tag.getName());
        assertEquals("<sender>John Smith</sender>", tag.getValue());
        assertTrue(tag.getAttributes().isEmpty());
        assertTrue(tag.getValues().isEmpty());
        assertEquals(content.length(), parseResult.getParserPos());
    }

    @Test
    public void testParseCommentValid() throws Exception {
        String content = "<!--COMMENT--><abc><![CDATA[<sender>John Smith</sender>]]></abc>";
        ParseResult<Tag> parseResult = tagFullToken.parse(content);
        Tag tag = parseResult.getValue();
        assertEquals("abc", tag.getName());
        assertEquals("<sender>John Smith</sender>", tag.getValue());
        assertTrue(tag.getAttributes().isEmpty());
        assertTrue(tag.getValues().isEmpty());
        assertEquals(content.length(), parseResult.getParserPos());
    }

    @Test
    public void testParseClosedValid() throws Exception {
        String content = "<abc/>";
        ParseResult<Tag> parseResult = tagFullToken.parse(content);
        Tag tag = parseResult.getValue();
        assertEquals("abc", tag.getName());
        assertNull(tag.getValue());
        assertEquals(content.length(), parseResult.getParserPos());
    }

    @Test
    public void testParseWithAttributesValid() throws Exception {
        String content = "<abc id=\"1\" price=\"100.02\">data</abc>";
        ParseResult<Tag> parseResult = tagFullToken.parse(content);
        Tag tag = parseResult.getValue();
        assertEquals("abc", tag.getName());
        assertEquals("data", tag.getValue());
        assertEquals(2, tag.getAttributes().size());
        assertEquals("id", tag.getAttributes().get(0).getName());
        assertEquals("1", tag.getAttributes().get(0).getValue());
        assertEquals("price", tag.getAttributes().get(1).getName());
        assertEquals("100.02", tag.getAttributes().get(1).getValue());
        assertTrue(tag.getValues().isEmpty());
        assertEquals(content.length(), parseResult.getParserPos());
    }

    @Test
    public void testParseCompositeValid() throws Exception {
        String content = "<composition><abc>data</abc></composition>";
        ParseResult<Tag> parseResult = tagFullToken.parse(content);
        Tag tag = parseResult.getValue();
        assertEquals("composition", tag.getName());
        assertNull(tag.getValue());
        assertTrue(tag.getAttributes().isEmpty());
        assertEquals(1, tag.getValues().size());
        assertEquals(content.length(), parseResult.getParserPos());
    }

    @Test
    public void testParseCompositeInsideValid() throws Exception {
        String content = "<composition><abc>data</abc></composition>";
        ParseResult<Tag> parseResult = tagFullToken.parse(content, content.indexOf("<abc>"));
        Tag tag = parseResult.getValue();
        assertEquals("abc", tag.getName());
        assertEquals("data", tag.getValue());
        assertTrue(tag.getAttributes().isEmpty());
        assertTrue(tag.getValues().isEmpty());
        assertEquals(content.indexOf("</composition>"), parseResult.getParserPos());
    }

    @Test(expected = ParseException.class)
    public void testParseWrongCloseTagFailed() throws Exception {
        String content = "<abc>data</abc1>";
        tagFullToken.parse(content);
    }

    @Test(expected = ParseException.class)
    public void testParseNoCloseTagFailed() throws Exception {
        String content = "<abc>data";
        tagFullToken.parse(content);
    }

    @Test(expected = ParseException.class)
    public void testParseWithDublicatedKeysFailed() throws Exception {
        String content = "<abc id=\"1\" id=\"2\"/>";
        tagFullToken.parse(content).getValue();
    }
}