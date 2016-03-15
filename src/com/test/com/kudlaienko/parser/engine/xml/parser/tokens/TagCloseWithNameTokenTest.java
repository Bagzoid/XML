package com.kudlaienko.parser.engine.xml.parser.tokens;

import com.kudlaienko.parser.engine.xml.ParserDecorator;
import com.kudlaienko.parser.shell.exceptions.ParseException;
import com.kudlaienko.parser.shell.ParseResult;
import com.kudlaienko.parser.shell.Parser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TagCloseWithNameTokenTest {

    private Parser<String, String> tagCloseToken;

    @Before
    public void setUp() {
        tagCloseToken = ParserDecorator.getTagCloseWithNameToken();
    }

    @Test
    public void testParseLowerCaseValid() throws Exception {
        String tag = "</abcd>";
        ParseResult<String> parseResult = tagCloseToken.parse(tag);
        assertEquals("abcd", parseResult.getValue());
        assertEquals(tag.length(), parseResult.getParserPos());
    }

    @Test
    public void testParseUpperCaseValid() throws Exception {
        String tag = "</ABCD>";
        ParseResult<String> parseResult = tagCloseToken.parse(tag);
        assertEquals("ABCD", parseResult.getValue());
        assertEquals(tag.length(), parseResult.getParserPos());
    }

    @Test
    public void testParseFirstTagValid() throws Exception {
        String tag = "</abcd><ab2></ab2>";
        ParseResult<String> parseResult = tagCloseToken.parse(tag);
        assertEquals("abcd", parseResult.getValue());
        assertEquals("</abcd>".length(), parseResult.getParserPos());
    }

    @Test
    public void testParseEndTagValid() throws Exception {
        String tag = "<abcd></abcd></ab2>";
        ParseResult<String> parseResult = tagCloseToken.parse(tag, tag.indexOf("</ab2>"));
        assertEquals("ab2", parseResult.getValue());
        assertEquals(tag.length(), parseResult.getParserPos());
    }

    @Test
    public void testParseTagInsideValid() throws Exception {
        String tag = "<abcd></abcd><ab2></ab2>";
        ParseResult<String> parseResult = tagCloseToken.parse(tag, tag.indexOf("</abcd>"));
        assertEquals("abcd", parseResult.getValue());
        assertEquals(tag.indexOf("</abcd>") + "</abcd>".length(), parseResult.getParserPos());
    }

    @Test
    public void testParseNewLineValid() throws Exception {
        String tag = "\n</abcd>\n";
        ParseResult<String> parseResult = tagCloseToken.parse(tag);
        assertEquals("abcd", parseResult.getValue());
        assertEquals(tag.length(), parseResult.getParserPos());
    }

    @Test
    public void testParseTabulationValid() throws Exception {
        String tag = "\t</abcd>\t";
        ParseResult<String> parseResult = tagCloseToken.parse(tag);
        assertEquals("abcd", parseResult.getValue());
        assertEquals(tag.length(), parseResult.getParserPos());
    }

    @Test
    public void testParseInnerSpacesValid() throws Exception {
        String tag = "</   abcd   >";
        ParseResult<String> parseResult = tagCloseToken.parse(tag);
        assertEquals("abcd", parseResult.getValue());
        assertEquals(tag.length(), parseResult.getParserPos());
    }

    @Test
    public void testParseOuterSpacesValid() throws Exception {
        String tag = "    </abcd>    ";
        ParseResult<String> parseResult = tagCloseToken.parse(tag);
        assertEquals("abcd", parseResult.getValue());
        assertEquals(tag.length(), parseResult.getParserPos());
    }

    @Test
    public void testParseBothSpacesValid() throws Exception {
        String tag = " </   abcd   >  ";
        ParseResult<String> parseResult = tagCloseToken.parse(tag);
        assertEquals("abcd", parseResult.getValue());
        assertEquals(tag.length(), parseResult.getParserPos());
    }

    @Test(expected = ParseException.class)
    public void testParseFailed() throws Exception {
        String tag = " </ab cd>";
        tagCloseToken.parse(tag);
    }

    @Test(expected = ParseException.class)
    public void testParseWrongTagFailed() throws Exception {
        String tag = " </abcd";
        tagCloseToken.parse(tag);
    }

    @Test(expected = ParseException.class)
    public void testParseTextAsTagFailed() throws Exception {
        String tag = "abcd";
        tagCloseToken.parse(tag);
    }

    @Test(expected = ParseException.class)
    public void testParseEmptyStringFailed() throws Exception {
        String tag = "";
        tagCloseToken.parse(tag);
    }

    @Test(expected = ParseException.class)
    public void testParseFromBeginningFailed() throws Exception {
        String tag = "<abc></abc>";
        System.out.println(tagCloseToken.parse(tag));
    }

}