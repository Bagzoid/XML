package com.kudlaienko.parser.engine.xml.parser.tokens;

import com.kudlaienko.parser.shell.exceptions.ParseException;
import com.kudlaienko.parser.shell.ParseResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * Parse tag opening and extract tag name
 * e.g. "<NAME>" or "<NAME id="1">" will be parsed as "<NAME" and result will be "NAME"
 */
public class TagOpenNameToken extends CustomToken<String, String> {
    private Pattern pattern;

    @Override
    public ParseResult<String> doParse(String content, int start) throws ParseException {
        Matcher matcher = pattern.matcher(content);
        if (!matcher.find(start) || matcher.start() != start)
            throw new ParseException("Tag open parsing failed: " + content);

        int parserPos = matcher.start() + matcher.group().length();
        return new ParseResult<>(matcher.group(1), parserPos);
    }

    @Override
    protected void init() {
        pattern = Pattern.compile("[\\s]*<[ ]*([A-Za-z_0-9]+)[\\s]*");
    }
}
