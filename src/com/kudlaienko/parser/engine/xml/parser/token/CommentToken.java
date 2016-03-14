package com.kudlaienko.parser.engine.xml.parser.token;

import com.kudlaienko.parser.engine.xml.property.Attribute;
import com.kudlaienko.parser.shell.exceptions.ParseException;
import com.kudlaienko.parser.shell.service.ParseResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final public class CommentToken extends XmlToken<String, String> {
    private Pattern pattern;

    @Override
    public ParseResult<String> doParse(String content, int start) throws ParseException {
        Matcher matcher = pattern.matcher(content);
        if (!matcher.find(start) || matcher.start() != start)
            throw new ParseException("Comment parsing failed at char " + start);

        int parserPos = matcher.start() + matcher.group().length();
        String value = matcher.group("text");

        return new ParseResult<>(value, parserPos);
    }

    @Override
    protected void init() {
        pattern = Pattern.compile("(<!--){1}(?<text>(.|\n)*?)(-->){1}");
    }
}
