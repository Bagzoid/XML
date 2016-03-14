package com.kudlaienko.parser.engine.xml.parser.token;

public class XmlTokenFactory {
    private static AttributeListToken attributeListToken;
    private static AttributeToken attributeToken;
    private static DocumentMainTagToken documentMainTagToken;
    private static DocumentToken documentToken;
    private static TagCloseBracketToken tagCloseBracketToken;
    private static TagCloseWithNameToken tagCloseWithNameToken;
    private static TagFullToken tagFullToken;
    private static TagHeaderToken tagHeaderToken;
    private static TagOpenNameToken tagOpenNameToken;
    private static TagTextToken tagTextToken;
    private static CommentToken commentToken;

    public static AttributeListToken getAttributeListToken() {
        if (attributeListToken == null) {
            AttributeToken attributeToken = getAttributeToken();
            attributeListToken = new AttributeListToken(attributeToken);
        }
        return attributeListToken;
    }

    public static AttributeToken getAttributeToken() {
        if (attributeToken == null) {
            attributeToken = new AttributeToken();
        }
        return attributeToken;
    }

    public static DocumentMainTagToken getDocumentMainTagToken() {
        if (documentMainTagToken == null) {
            TagFullToken tagFullToken = getTagFullToken();
            documentMainTagToken = new DocumentMainTagToken(tagFullToken);
        }
        return documentMainTagToken;
    }

    public static DocumentToken getDocumentToken() {
        if (documentToken == null) {
            TagHeaderToken tagHeaderToken = getTagHeaderToken();
            DocumentMainTagToken documentMainTagToken = getDocumentMainTagToken();
            documentToken = new DocumentToken(tagHeaderToken, documentMainTagToken);
        }
        return documentToken;
    }

    public static TagCloseBracketToken getTagCloseBracketToken() {
        if (tagCloseBracketToken == null) {
            tagCloseBracketToken = new TagCloseBracketToken();
        }
        return tagCloseBracketToken;
    }

    public static TagCloseWithNameToken getTagCloseWithNameToken() {
        if (tagCloseWithNameToken == null) {
            tagCloseWithNameToken = new TagCloseWithNameToken();
        }
        return tagCloseWithNameToken;
    }

    public static TagFullToken getTagFullToken() {
        if (tagFullToken == null) {
            TagTextToken tagTextToken = getTagTextToken();
            TagCloseWithNameToken tagCloseWithNameToken = getTagCloseWithNameToken();
            TagCloseBracketToken tagCloseBracketToken = getTagCloseBracketToken();
            TagOpenNameToken tagOpenNameToken = getTagOpenNameToken();
            AttributeListToken attributeListToken = getAttributeListToken();
            CommentToken commentToken = getCommentToken();
            tagFullToken = new TagFullToken(tagTextToken, tagCloseWithNameToken, tagCloseBracketToken,
                    tagOpenNameToken, attributeListToken, commentToken);
        }
        return tagFullToken;
    }

    public static TagHeaderToken getTagHeaderToken() {
        if (tagHeaderToken == null) {
            tagHeaderToken = new TagHeaderToken();
        }
        return tagHeaderToken;
    }

    public static TagOpenNameToken getTagOpenNameToken() {
        if (tagOpenNameToken == null) {
            tagOpenNameToken = new TagOpenNameToken();
        }
        return tagOpenNameToken;
    }

    public static TagTextToken getTagTextToken() {
        if (tagTextToken == null) {
            CommentToken commentToken = getCommentToken();
            tagTextToken = new TagTextToken(commentToken);
        }
        return tagTextToken;
    }

    public static CommentToken getCommentToken() {
        if (commentToken == null) {
            commentToken = new CommentToken();
        }
        return commentToken;
    }
}
