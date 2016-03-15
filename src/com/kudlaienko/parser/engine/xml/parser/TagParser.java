package com.kudlaienko.parser.engine.xml.parser;

import com.kudlaienko.parser.engine.xml.parser.tokens.CustomToken;
import com.kudlaienko.parser.engine.xml.property.Attribute;
import com.kudlaienko.parser.engine.xml.parser.enums.Bracket;
import com.kudlaienko.parser.engine.xml.property.Tag;
import com.kudlaienko.parser.engine.xml.property.TagBuilder;
import com.kudlaienko.parser.shell.exceptions.ParseException;
import com.kudlaienko.parser.shell.ParseResult;
import com.kudlaienko.parser.shell.Parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser for tag construction e.g. <tag id="1">Text</id>
 */
public class TagParser extends CustomToken<String, Tag> {
    final private Parser<String, String> tagTextToken;
    final private Parser<String, String> tagCloseWithNameToken;
    final private Parser<String, Bracket> tagCloseBracketToken;
    final private Parser<String, String> tagOpenNameToken;
    final private Parser<String, Attribute> attributeParser;
    final private Parser<String, String> commentToken;

    @Override
    protected ParseResult<Tag> doParse(String content, int start) throws ParseException {
        TagBuilder tagBuilder = new TagBuilder();
        start = parseComments(content, start);
        start = parseName(content, start, tagBuilder);
        start = parseAttributes(content, start, tagBuilder);

        ParseResult<Bracket> closingBracket = getClosingBracket(content, start);
        Bracket bracket = closingBracket.getValue();
        start = closingBracket.getParserPos();

        switch (bracket) {
            case CLOSE:
                start = parseChildTags(content, start, tagBuilder);
                if (tagBuilder.getTags() == null || tagBuilder.getTags().isEmpty()) {
                    start = parseText(content, tagBuilder, start);
                }
                start = parseClosingTag(content, start, tagBuilder);
                break;
            case AUTO_CLOSE:
                break;
            default:
                throw new ParseException("Unexpected closing tag! Char: " + start);
        }

        Tag tag = tagBuilder.createTag();

        return new ParseResult<>(tag, start);
    }

    private int parseComments(String content, int start) {
        final int[] pos = {start};
        commentToken.parseAll(content, start).forEach(c -> pos[0] = c.getParserPos());
        start = pos[0];
        return start;
    }

    @Override
    protected void init() {
    }

    private int parseChildTags(String content, int start, TagBuilder tagBuilder) throws ParseException {
        List<Tag> tags = new ArrayList<>();
        for (ParseResult<Tag> tag : this.parseAll(content, start)) {
            tags.add(tag.getValue());
            start = tag.getParserPos();
        }
        tagBuilder.setTags(tags);
        return start;
    }

    private int parseText(String content, TagBuilder tagBuilder, int start) throws ParseException {
        final Integer[] parserPos = {start};
        tagTextToken.isParsable(content, start, parserResult -> {
            String text = parserResult.getValue();
            parserPos[0] = parserResult.getParserPos();
            tagBuilder.setValue(text);

        });
        return parserPos[0];
    }

    private ParseResult<Bracket> getClosingBracket(String content, int start) throws ParseException {
        return tagCloseBracketToken.parse(content, start);
    }

    private int parseClosingTag(String content, int start, TagBuilder tagBuilder) throws ParseException {
        ParseResult<String> closeName = tagCloseWithNameToken.parse(content, start);
        if (!tagBuilder.getName().equalsIgnoreCase(closeName.getValue())) {
            throw new ParseException("Close tag not equal to open tag name: " + tagBuilder.getName() + " <> " + closeName);
        }

        return closeName.getParserPos();
    }

    private int parseAttributes(String content, int start, TagBuilder tagBuilder) throws ParseException {
        int parserPos = start;
        List<Attribute> attributes = new ArrayList<>();
        for (ParseResult<Attribute> attribute : attributeParser.parseAll(content, start)) {
            parserPos = attribute.getParserPos();
            attributes.add(attribute.getValue());
        }
        if (!attributes.isEmpty()) {
            tagBuilder.setAttributes(attributes);
        }
        return parserPos;
    }

    private int parseName(String content, int start, TagBuilder tagBuilder) throws ParseException {
        ParseResult<String> tagName = tagOpenNameToken.parse(content, start);
        tagBuilder.setName(tagName.getValue());

        return tagName.getParserPos();
    }

    public TagParser(Parser<String, String> tagTextToken,
                     Parser<String, String> tagCloseWithNameToken,
                     Parser<String, Bracket> tagCloseBracketToken,
                     Parser<String, String> tagOpenNameToken,
                     Parser<String, Attribute> attributeParser,
                     Parser<String, String> commentToken) {
        this.tagTextToken = tagTextToken;
        this.tagCloseWithNameToken = tagCloseWithNameToken;
        this.tagCloseBracketToken = tagCloseBracketToken;
        this.tagOpenNameToken = tagOpenNameToken;
        this.attributeParser = attributeParser;
        this.commentToken = commentToken;
    }
}
