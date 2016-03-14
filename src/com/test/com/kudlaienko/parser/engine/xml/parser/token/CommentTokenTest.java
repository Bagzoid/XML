package com.kudlaienko.parser.engine.xml.parser.token;

import com.kudlaienko.parser.shell.exceptions.ParseException;
import com.kudlaienko.parser.shell.service.ParseResult;
import com.kudlaienko.parser.shell.service.Parser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommentTokenTest {

    private Parser<String, String> commentParser;

    @Before
    public void setUp() {
        commentParser = XmlTokenFactory.getCommentToken();
    }

    @Test
    public void testCommentValid() throws ParseException {
        String contnet = "<!--abc-->";
        ParseResult<String> parseResult = commentParser.parse(contnet);
        assertEquals("abc", parseResult.getValue());
    }

    @Test
    public void testCommentEmptyValid() throws ParseException {
        String contnet = "<!---->";
        ParseResult<String> parseResult = commentParser.parse(contnet);
        assertEquals("", parseResult.getValue());
    }

    @Test
    public void testCommentWithNeweLineValid() throws ParseException {
        String contnet = "<!--ab\ncd-->";
        ParseResult<String> parseResult = commentParser.parse(contnet);
        assertEquals("ab\ncd", parseResult.getValue());
    }

    @Test
    public void testCommentFirstParsedValid() throws ParseException {
        String contnet = "<!--abcd1--><!--abcd2-->";
        ParseResult<String> parseResult = commentParser.parse(contnet);
        assertEquals("abcd1", parseResult.getValue());
        assertEquals(contnet.indexOf("<!--abcd2"), parseResult.getParserPos());
    }


}