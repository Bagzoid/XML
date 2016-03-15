package com.kudlaienko.parser.engine.xml.parser.tokens;

import com.kudlaienko.parser.engine.xml.parser.enums.Bracket;
import com.kudlaienko.parser.shell.exceptions.ParseException;
import com.kudlaienko.parser.shell.ParseResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * Parse tag closing bracket: "/>", ">"
 * Return parsed Bracket type
 */
public class TagCloseBracketToken extends CustomToken<String, Bracket> {
    private Pattern pattern;

    @Override
    protected ParseResult<Bracket> doParse(String content, int start) throws ParseException {
        Matcher matcher = pattern.matcher(content);
        if (!matcher.find(start) || matcher.start() != start)
            throw new ParseException("Close bracket parsing failed at char " + start);

        Bracket bracket = Bracket.getEnum(matcher.group(1));

        int parserPos = matcher.start() + matcher.group().length();

        return new ParseResult<>(bracket, parserPos);
    }

    @Override
    protected void init() {
        pattern = Pattern.compile("[\\s]*(/>|>)[\\s]*");
    }

}
