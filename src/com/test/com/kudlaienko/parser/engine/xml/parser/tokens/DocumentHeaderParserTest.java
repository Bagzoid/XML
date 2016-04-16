package com.kudlaienko.parser.engine.xml.parser.tokens;

import com.kudlaienko.parser.engine.xml.ParserFactoryImpl;
import com.kudlaienko.parser.shell.exceptions.ParseException;
import com.kudlaienko.parser.shell.ParseResult;
import com.kudlaienko.parser.shell.Parser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class DocumentHeaderParserTest {

    private Parser<String, String> tagHeaderToken;

    @Before
    public void setUp() {
        tagHeaderToken = new ParserFactoryImpl().getDocumentHeaderParser();
    }

    @Test
    public void testParseHeaderValid() throws Exception {
        String tag = "<?xml version=\"1.1\" encoding=\"UTF-8\" ?>";
        ParseResult<String> parseResult = tagHeaderToken.parse(tag);
        assertEquals(tag, parseResult.getValue());
        assertEquals(tag.length(), parseResult.getParserPos());
    }

    @Test(expected = ParseException.class)
    public void testParseHeaderFailed() throws Exception {
        String tag = "<?xml version=\"1.1\" encoding=\"UTF-8\" >";
        tagHeaderToken.parse(tag);
    }

    @Test
    public void testParseHeaderWithNewLinesAfterValid() throws Exception {
        String tag = "<?xml version=\"1.1\" encoding=\"UTF-8\" ?>";
        ParseResult<String> parseResult = tagHeaderToken.parse(tag + "\n\n");
        assertEquals(tag, parseResult.getValue());
        assertEquals((tag + "\n\n").length(), parseResult.getParserPos());
    }

}