package com.kudlaienko.parser.engine.xml.parser;


import com.kudlaienko.parser.engine.xml.parser.tokens.CustomToken;
import com.kudlaienko.parser.engine.xml.property.Document;
import com.kudlaienko.parser.engine.xml.property.Tag;
import com.kudlaienko.parser.shell.exceptions.ParseException;
import com.kudlaienko.parser.shell.ParseResult;
import com.kudlaienko.parser.shell.Parser;

public class DocumentParser extends CustomToken<String, Document> {
    final private Parser<String, String> headerParser;
    final private Parser<String, Tag> mainTagParser;

    @Override
    protected ParseResult<Document> doParse(String content, int start) throws ParseException {
        ParseResult<String> header = headerParser.parse(content, start);
        ParseResult<Tag> mainTag = mainTagParser.parse(content, header.getParserPos());
        boolean isAllParsed = (mainTag.getParserPos() == content.length());
        if (!isAllParsed) {
            throw new ParseException("Document contains unexpected data. Char: " + mainTag.getParserPos());
        }
        Document document = new Document(header.getValue(), mainTag.getValue());
        return new ParseResult<>(document, mainTag.getParserPos());
    }

    @Override
    protected void init() {
    }

    public DocumentParser(Parser<String, String> headerParser, Parser<String, Tag> mainTagParser) {
        this.headerParser = headerParser;
        this.mainTagParser = mainTagParser;
    }
}
