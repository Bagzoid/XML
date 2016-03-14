package com.kudlaienko.parser.engine.xml.parser.token;

import com.kudlaienko.parser.engine.xml.property.Attribute;
import com.kudlaienko.parser.shell.exceptions.ParseException;
import com.kudlaienko.parser.shell.service.ParseResult;
import com.kudlaienko.parser.shell.service.Parser;

import java.util.ArrayList;
import java.util.List;

//TODO: Think about to use AttributeToken.parseAll instead of separate token
final public class AttributeListToken extends XmlToken<String, List<Attribute>> {
    private Parser<String, Attribute> attributeParser;

    @Override
    public ParseResult<List<Attribute>> doParse(String content, int start) throws ParseException {
        List<Attribute> attributeList = new ArrayList<>();
        for (ParseResult<Attribute> attribute : attributeParser.parseAll(content, start)) {
            attributeList.add(attribute.getValue());
            start = attribute.getParserPos();
        }

        if (attributeList.isEmpty()) {
            throw new ParseException("Attributes not found at char " + start);
        }

        return new ParseResult<>(attributeList, start);
    }

    @Override
    protected void init() {
    }

    public AttributeListToken(Parser<String, Attribute> attributeParser) {
        this.attributeParser = attributeParser;
    }
}
