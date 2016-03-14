package com.kudlaienko.parser.engine.xml.parser.token;

import com.kudlaienko.parser.engine.xml.property.Tag;
import com.kudlaienko.parser.shell.exceptions.ParseException;
import com.kudlaienko.parser.shell.service.ParseResult;
import com.kudlaienko.parser.shell.service.Parser;

final public class DocumentMainTagToken extends XmlToken<String, Tag> {
    private Parser<String, Tag> tagParser;

    @Override
    protected ParseResult<Tag> doParse(String content, int start) throws ParseException {

        ParseResult<Tag> tagList = tagParser.parse(content, start);

        if (content.length() > tagList.getParserPos()) {
            throw new ParseException("Document contains unexpected data. Char: " + tagList.getParserPos());
        }

        return tagList;
    }

    @Override
    protected void init() {

    }

    public DocumentMainTagToken(Parser<String, Tag> tagParser) {
        this.tagParser = tagParser;
    }
}
