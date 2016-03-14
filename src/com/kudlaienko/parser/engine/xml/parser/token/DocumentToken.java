package com.kudlaienko.parser.engine.xml.parser.token;


import com.kudlaienko.parser.engine.xml.property.Document;
import com.kudlaienko.parser.engine.xml.property.Tag;
import com.kudlaienko.parser.shell.exceptions.ParseException;
import com.kudlaienko.parser.shell.service.ParseResult;
import com.kudlaienko.parser.shell.service.Parser;

public class DocumentToken extends XmlToken<String, Document> {
    final private Parser<String, String> headerParser;
    final private Parser<String, Tag> mainTagParser;

    @Override
    protected ParseResult<Document> doParse(String content, int start) throws ParseException {
        ParseResult<String> header = headerParser.parse(content, start);
        ParseResult<Tag> mainTag = mainTagParser.parse(content, header.getParserPos());

        Document document = new Document(header.getValue(), mainTag.getValue());
        return new ParseResult<>(document, mainTag.getParserPos());
    }

    @Override
    protected void init() {
    }

    public DocumentToken(Parser<String, String> headerParser, Parser<String, Tag> mainTagParser) {
        this.headerParser = headerParser;
        this.mainTagParser = mainTagParser;
    }
}
