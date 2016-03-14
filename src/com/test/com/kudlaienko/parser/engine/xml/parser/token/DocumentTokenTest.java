package com.kudlaienko.parser.engine.xml.parser.token;

import com.kudlaienko.parser.engine.xml.property.Document;
import com.kudlaienko.parser.engine.xml.property.Tag;
import com.kudlaienko.parser.shell.service.ParseResult;
import com.kudlaienko.parser.shell.service.Parser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DocumentTokenTest {
    private Parser<String, Document> xmlParser;
    private String content = "<?xml version=\"1.1\" encoding=\"UTF-8\" ?>\n\n" +
            "<main>\n" +
            "<objects>\n" +
            "<object>\n" +
            "<id>\n" +
            "12000A" +
            "</id>\n" +
            "</object>\n" +
            "</objects>\n" +
            "</main>\n";

    @Before
    public void setUp() {
        xmlParser = XmlTokenFactory.getDocumentToken();
    }

    @After
    public void setDown() throws Exception {
    }

    @Test
    public void testParseValid() throws Exception {
        ParseResult<Document> parseResult = xmlParser.parse(content);
        Document document = parseResult.getValue();
        assertEquals(content.length(), parseResult.getParserPos());
        assertEquals("<?xml version=\"1.1\" encoding=\"UTF-8\" ?>", document.getHeader());

        Tag mainTag = document.getMainTag();
        assertEquals("main", mainTag.getName());
        assertNull(mainTag.getValue());

        Tag tagObjects = mainTag.getValues().get(0);
        assertEquals("objects", tagObjects.getName());
        assertNull(tagObjects.getValue());
    }

    @Test
    public void testParseValidaationFailed() throws Exception {
        ParseResult<Document> parseResult = xmlParser.parse(content);
        Document document = parseResult.getValue();
        assertEquals(content.length(), parseResult.getParserPos());
        assertEquals("<?xml version=\"1.1\" encoding=\"UTF-8\" ?>", document.getHeader());

        Tag mainTag = document.getMainTag();
        assertEquals("main", mainTag.getName());
        assertNull(mainTag.getValue());

        Tag tagObjects = mainTag.getValues().get(0);
        assertEquals("objects", tagObjects.getName());
        assertNull(tagObjects.getValue());
    }

}