package com.kudlaienko.parser.engine.xml;

import com.kudlaienko.parser.engine.xml.parser.*;
import com.kudlaienko.parser.engine.xml.parser.tokens.TagCloseBracketToken;
import com.kudlaienko.parser.engine.xml.parser.tokens.TagCloseWithNameToken;
import com.kudlaienko.parser.engine.xml.parser.tokens.TagOpenNameToken;
import com.kudlaienko.parser.engine.xml.parser.tokens.TagTextToken;

public interface ParserFactory {
    AttributeParser getAttributeParser();

    DocumentParser getDocumentParser();

    TagCloseBracketToken getTagCloseBracketToken();

    TagCloseWithNameToken getTagCloseWithNameToken();

    TagParser getTagParser();

    DocumentHeaderParser getDocumentHeaderParser();

    TagOpenNameToken getTagOpenNameToken();

    TagTextToken getTagTextToken();

    CommentParser getCommentParser();
}
