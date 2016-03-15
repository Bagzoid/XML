package com.kudlaienko.parser.engine.xml.parser.tokens;

import com.kudlaienko.parser.engine.xml.parser.enums.Bracket;
import com.kudlaienko.parser.engine.xml.ParserDecorator;
import com.kudlaienko.parser.shell.ParseResult;
import com.kudlaienko.parser.shell.Parser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TagCloseBracketTokenTest {

    private Parser<String, Bracket> bracketParser;

    @Before
    public void setUp() {
        bracketParser = ParserDecorator.getTagCloseBracketToken();
    }

    @Test
    public void testParseCloseBracketType1() throws Exception {
        String content = ">";
        ParseResult<Bracket> parseResult = bracketParser.parse(content);
        Bracket bracket = parseResult.getValue();
        assertEquals(Bracket.CLOSE, bracket);
        assertEquals(content.length(), parseResult.getParserPos());
    }

    @Test
    public void testParseCloseBracketType2() throws Exception {
        String content = "/>";
        ParseResult<Bracket> parseResult = bracketParser.parse(content);
        Bracket bracket = parseResult.getValue();
        assertEquals(Bracket.AUTO_CLOSE, bracket);
        assertEquals(content.length(), parseResult.getParserPos());
    }

    @Test
    public void testParseCloseBracketType1WithSpaces() throws Exception {
        String content = " /> \n\t  ";
        ParseResult<Bracket> parseResult = bracketParser.parse(content);
        Bracket bracket = parseResult.getValue();
        assertEquals(Bracket.AUTO_CLOSE, bracket);
        assertEquals(content.length(), parseResult.getParserPos());
    }
}