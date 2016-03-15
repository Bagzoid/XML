package com.kudlaienko.parser.engine.xml.parser.tokens;

import com.kudlaienko.parser.shell.exceptions.ParseException;
import com.kudlaienko.parser.shell.ParseResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * Parse tag closing with name
 * e.g. </NAME> will be fully parsed (as "</NAME>") and result will be "NAME"
 */
public class TagCloseWithNameToken extends CustomToken<String, String> {
    private Pattern pattern;

    @Override
    public ParseResult<String> doParse(String content, int start) throws ParseException {
        Matcher matcher = pattern.matcher(content);
        if (!matcher.find(start) || matcher.start() != start)
            throw new ParseException("Tag close parsing failed at char " + start);

        int parserPos = matcher.start() + matcher.group().length();
        return new ParseResult<>(matcher.group(1), parserPos);
    }

    @Override
    protected void init() {
        pattern = Pattern.compile("[\\s]*</[ ]*([A-Za-z_0-9]*)[ ]*>[\\s]*");
    }
}
