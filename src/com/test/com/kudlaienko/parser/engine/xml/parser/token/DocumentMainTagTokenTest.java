package com.kudlaienko.parser.engine.xml.parser.token;

import com.kudlaienko.parser.engine.xml.property.Tag;
import com.kudlaienko.parser.shell.exceptions.ParseException;
import com.kudlaienko.parser.shell.service.ParseResult;
import com.kudlaienko.parser.shell.service.Parser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class DocumentMainTagTokenTest {
    private Parser<String, Tag> tagListToken;

    @Before
    public void setUp() {
        tagListToken = XmlTokenFactory.getDocumentMainTagToken();
    }

    @Test
    public void testParseOneTagValid() throws Exception {
        String content = "<abc> data </abc>";
        ParseResult<Tag> parseResult = tagListToken.parse(content);
        Tag tag = parseResult.getValue();

        assertEquals("abc", tag.getName());
        assertEquals("data", tag.getValue());
        assertEquals(content.length(), parseResult.getParserPos());
    }

    @Test
    public void testParseCompositeValid() throws Exception {
        String content = "<composition>  <abc>data</abc>   </composition>";
        ParseResult<Tag> parseResult = tagListToken.parse(content);
        Tag tag = parseResult.getValue();

        assertEquals("composition", tag.getName());
        assertNull(tag.getValue());
        assertEquals("abc", tag.getValues().get(0).getName());
        assertEquals("data", tag.getValues().get(0).getValue());
        assertEquals(tag.getValues().size(), 1);
        assertEquals(content.length(), parseResult.getParserPos());
    }

    @Test
    public void testParseCompositeWithCommentsValid() throws Exception {
        String content = "<composition> <!--comments--> <abc>data</abc>   </composition>";
        ParseResult<Tag> parseResult = tagListToken.parse(content);
        Tag tag = parseResult.getValue();

        assertEquals("composition", tag.getName());
        assertNull(tag.getValue());
        assertEquals("abc", tag.getValues().get(0).getName());
        assertEquals("data", tag.getValues().get(0).getValue());
        assertEquals(tag.getValues().size(), 1);
        assertEquals(content.length(), parseResult.getParserPos());
    }

    @Test(expected = ParseException.class)
    public void testParseDoubleMainTagFailed() throws Exception {
        String content = "<composition>  <abc>data</abc>   </composition><composition>abc</composition>";
        tagListToken.parse(content);
    }

    @Test(expected = ParseException.class)
    public void testParseEmptyFailed() throws Exception {
        String content = "";
        tagListToken.parse(content);
    }

    @Test(expected = ParseException.class)
    public void testParseSpacesFailed() throws Exception {
        String content = "    ";
        tagListToken.parse(content);
    }

    @Test(expected = ParseException.class)
    public void testParseSpacesWithNewLinesFailed() throws Exception {
        String content = "  \n\n  ";
        tagListToken.parse(content);
    }
}