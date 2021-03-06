package com.kudlaienko.parser.engine.xml.parser.tokens;

import com.kudlaienko.parser.engine.xml.ParserFactoryImpl;
import com.kudlaienko.parser.shell.ParseResult;
import com.kudlaienko.parser.shell.Parser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TagTextTokenTest {

    private Parser<String, String> tagTextToken;

    @Before
    public void setUp() {
        tagTextToken = new ParserFactoryImpl().getTagTextToken();
    }

    @Test
    public void testTextValid() throws Exception {
        String text = "abcd";
        ParseResult<String> parseResult = tagTextToken.parse(text);
        assertEquals(text, parseResult.getValue());
        assertEquals(text.length(), parseResult.getParserPos());
    }

    @Test
    public void testTextWithSpacesInTheBeginningAbdInTheEndValid() throws Exception {
        String text = "    abcd    ";
        ParseResult<String> parseResult = tagTextToken.parse(text);
        assertEquals("abcd", parseResult.getValue());
        assertEquals(text.length(), parseResult.getParserPos());
    }

    @Test
    public void testTextWithCDataValid() throws Exception {
        String text =  "<![CDATA[<sender>John Smith</sender>]]>";
        ParseResult<String> parseResult = tagTextToken.parse(text);
        assertEquals("<sender>John Smith</sender>", parseResult.getValue());
        assertEquals(text.length(), parseResult.getParserPos());
    }

    @Test
    public void testTextWithDoubleCDataValid() throws Exception {
        String text =  "<![CDATA[<sender>John Smith</sender>]]><![CDATA[abcd]]>";
        ParseResult<String> parseResult = tagTextToken.parse(text);
        assertEquals("<sender>John Smith</sender>abcd", parseResult.getValue());
        assertEquals(text.length(), parseResult.getParserPos());
    }

    @Test
    public void testTextWithMixedBlocksValid() throws Exception {
        String text =  "<![CDATA[<sender>John Smith</sender>]]> is a VIP <![CDATA[person]]>";
        ParseResult<String> parseResult = tagTextToken.parse(text);
        assertEquals("<sender>John Smith</sender> is a VIP person", parseResult.getValue());
        assertEquals(text.length(), parseResult.getParserPos());
    }

    @Test
    public void testTextWithSpacesInTheBeginningAndInTheMiddleValid() throws Exception {
        String text = "    ab  cd";
        ParseResult<String> parseResult = tagTextToken.parse(text);
        assertEquals("ab  cd", parseResult.getValue());
        assertEquals(text.length(), parseResult.getParserPos());
    }

    @Test
    public void testTextWithSpecialCharactersValid() throws Exception {
        String text = "abc<def";
        ParseResult<String> parseResult = tagTextToken.parse(text);
        assertEquals("abc", parseResult.getValue());
        assertEquals("abc".length(), parseResult.getParserPos());
    }

    @Test
    public void testTextWithSpecialCharacterValid() throws Exception {
        String text = "abc&lt;def";
        String expectedParseResult = "abc<def";
        ParseResult<String> parseResult = tagTextToken.parse(text);
        assertEquals(expectedParseResult, parseResult.getValue());
        assertEquals(text.length(), parseResult.getParserPos());
    }
}