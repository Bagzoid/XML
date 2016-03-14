package com.kudlaienko.parser.engine.xml.parser.token;

import com.kudlaienko.parser.shell.exceptions.ParseException;
import com.kudlaienko.parser.shell.service.ParseResult;
import com.kudlaienko.parser.shell.service.Parser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TagOpenNameTokenTest {

    private Parser<String, String> tagOpenToken;

    @Before
    public void setUp() {
        tagOpenToken = XmlTokenFactory.getTagOpenNameToken();
    }

    @Test
    public void testParseLowerCaseValid() throws Exception {
        String tag = "<abcd>";
        ParseResult<String> parseResult = tagOpenToken.parse(tag);
        assertEquals("abcd", parseResult.getValue());
        assertEquals(tag.length() - 1, parseResult.getParserPos());
    }

    @Test
    public void testParseUpperCaseValid() throws Exception {
        String tag = "<ABCD>";
        ParseResult<String> parseResult = tagOpenToken.parse(tag);
        assertEquals("ABCD", parseResult.getValue());
        assertEquals(tag.length() - 1, parseResult.getParserPos());
    }

    @Test
    public void testParseFirstTagValid() throws Exception {
        String tag = "<abcd></abcd><ab2></ab2>";
        ParseResult<String> parseResult = tagOpenToken.parse(tag);
        assertEquals("abcd", parseResult.getValue());
        assertEquals(5, parseResult.getParserPos());
    }

    @Test
    public void testParseSecondTagValid() throws Exception {
        String tag = "<abcd></abcd><ab2>";
        ParseResult<String> parseResult = tagOpenToken.parse(tag, 13);
        assertEquals("ab2", parseResult.getValue());
        assertEquals(tag.length() - 1, parseResult.getParserPos());
    }

    @Test
    public void testParseSecondTagWithCloseTagValid() throws Exception {
        String tag = "<abcd></abcd><ab2></ab2>";
        ParseResult<String> parseResult = tagOpenToken.parse(tag, tag.indexOf("<ab2>"));
        assertEquals("ab2", parseResult.getValue());
        assertEquals(tag.length() - "</ab2>".length() - 1, parseResult.getParserPos());
    }

    @Test
    public void testParseNewLineValid() throws Exception {
        String tag = "\n<abcd>\n";
        ParseResult<String> parseResult = tagOpenToken.parse(tag);
        assertEquals("abcd", parseResult.getValue());
        assertEquals(tag.length() - 2, parseResult.getParserPos());
    }

    @Test
    public void testParseTabulationValid() throws Exception {
        String tag = "\t<abcd>\t";
        ParseResult<String> parseResult = tagOpenToken.parse(tag);
        assertEquals("abcd", parseResult.getValue());
        assertEquals(tag.length() - 2, parseResult.getParserPos());
    }

    @Test
    public void testParseInnerSpacesValid() throws Exception {
        String tag = "<   abcd   >";
        ParseResult<String> parseResult = tagOpenToken.parse(tag);
        assertEquals("abcd", parseResult.getValue());
        assertEquals(tag.length() - 1, parseResult.getParserPos());
    }

    @Test
    public void testParseOuterSpacesValid() throws Exception {
        String tag = "    <abcd>    ";
        ParseResult<String> parseResult = tagOpenToken.parse(tag);
        assertEquals("abcd", parseResult.getValue());
        assertEquals(tag.length() - ">    ".length(), parseResult.getParserPos());
    }

    @Test
    public void testParseBothSpacesValid() throws Exception {
        String tag = " <   abcd   >  ";
        ParseResult<String> parseResult = tagOpenToken.parse(tag);
        assertEquals("abcd", parseResult.getValue());
        assertEquals(tag.length() - ">  ".length(), parseResult.getParserPos());
    }

    @Test(expected = ParseException.class)
    public void testParseFailed() throws Exception {
        String tag = " <<";
        tagOpenToken.parse(tag);
    }

    @Test(expected = ParseException.class)
    public void testParseWrongTagFailed() throws Exception {
        String tag = "><abcd";
        tagOpenToken.parse(tag).getValue();
    }

    @Test(expected = ParseException.class)
    public void testParseTextAsTagFailed() throws Exception {
        String tag = "abcd";
        tagOpenToken.parse(tag).getValue();
    }

    @Test(expected = ParseException.class)
    public void testParseEmptyStringFailed() throws Exception {
        String tag = "";
        tagOpenToken.parse(tag).getValue();
    }

    @Test(expected = ParseException.class)
    public void testParseFromBeginningFailed() throws Exception {
        String tag = "</abc><abc>";
        tagOpenToken.parse(tag, 0).getValue();
    }

}