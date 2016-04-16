package com.kudlaienko.parser.engine.xml;

import com.kudlaienko.parser.engine.xml.parser.*;
import com.kudlaienko.parser.engine.xml.parser.tokens.TagCloseBracketToken;
import com.kudlaienko.parser.engine.xml.parser.tokens.TagCloseWithNameToken;
import com.kudlaienko.parser.engine.xml.parser.tokens.TagOpenNameToken;
import com.kudlaienko.parser.engine.xml.parser.tokens.TagTextToken;

public class ParserFactoryImpl implements ParserFactory {
    private static AttributeParser attributeParser;
    private static DocumentParser documentParser;
    private static TagCloseBracketToken tagCloseBracketToken;
    private static TagCloseWithNameToken tagCloseWithNameToken;
    private static TagParser tagParser;
    private static DocumentHeaderParser documentHeaderParser;
    private static TagOpenNameToken tagOpenNameToken;
    private static TagTextToken tagTextToken;
    private static CommentParser commentParser;

    @Override
    public AttributeParser getAttributeParser() {
        if (attributeParser == null) {
            attributeParser = new AttributeParser();
        }
        return attributeParser;
    }

    @Override
    public DocumentParser getDocumentParser() {
        if (documentParser == null) {
            DocumentHeaderParser documentHeaderParser = getDocumentHeaderParser();
            TagParser tagParser = getTagParser();
            documentParser = new DocumentParser(documentHeaderParser, tagParser);
        }
        return documentParser;
    }

    @Override
    public TagCloseBracketToken getTagCloseBracketToken() {
        if (tagCloseBracketToken == null) {
            tagCloseBracketToken = new TagCloseBracketToken();
        }
        return tagCloseBracketToken;
    }

    @Override
    public TagCloseWithNameToken getTagCloseWithNameToken() {
        if (tagCloseWithNameToken == null) {
            tagCloseWithNameToken = new TagCloseWithNameToken();
        }
        return tagCloseWithNameToken;
    }

    @Override
    public TagParser getTagParser() {
        if (tagParser == null) {
            TagTextToken tagTextToken = getTagTextToken();
            TagCloseWithNameToken tagCloseWithNameToken = getTagCloseWithNameToken();
            TagCloseBracketToken tagCloseBracketToken = getTagCloseBracketToken();
            TagOpenNameToken tagOpenNameToken = getTagOpenNameToken();
            AttributeParser attributeParser = getAttributeParser();
            CommentParser commentParser = getCommentParser();
            tagParser = new TagParser(tagTextToken, tagCloseWithNameToken, tagCloseBracketToken,
                    tagOpenNameToken, attributeParser, commentParser);
        }
        return tagParser;
    }

    @Override
    public DocumentHeaderParser getDocumentHeaderParser() {
        if (documentHeaderParser == null) {
            documentHeaderParser = new DocumentHeaderParser();
        }
        return documentHeaderParser;
    }

    @Override
    public TagOpenNameToken getTagOpenNameToken() {
        if (tagOpenNameToken == null) {
            tagOpenNameToken = new TagOpenNameToken();
        }
        return tagOpenNameToken;
    }

    @Override
    public TagTextToken getTagTextToken() {
        if (tagTextToken == null) {
            CommentParser commentParser = getCommentParser();
            tagTextToken = new TagTextToken(commentParser);
        }
        return tagTextToken;
    }

    @Override
    public CommentParser getCommentParser() {
        if (commentParser == null) {
            commentParser = new CommentParser();
        }
        return commentParser;
    }
}
