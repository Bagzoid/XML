package com.kudlaienko.parser.engine.xml.parser.tokens;

import com.kudlaienko.parser.engine.xml.parser.tools.XmlStrings;
import com.kudlaienko.parser.shell.exceptions.ParseException;
import com.kudlaienko.parser.shell.ParseResult;
import com.kudlaienko.parser.shell.Parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * Parse text tag content
 */
public class TagTextToken extends CustomToken<String, String> {
    final private Parser<String, String> commentToken;
    private Pattern textPattern;
    private Pattern cdataPattern;

    public TagTextToken(Parser<String, String> commentToken) {
        this.commentToken = commentToken;
    }

    @Override
    protected ParseResult<String> doParse(String content, int start) throws ParseException {
        int pos = start;
        StringBuilder stringBuilder = new StringBuilder();
        ParseResult<String> text = parseText(content, pos);
        stringBuilder.append(text.getValue());
        pos = text.getParserPos();
        try {
            while (pos < content.length()) {
                text = parseText(content, text.getParserPos());
                pos = text.getParserPos();
                stringBuilder.append(text.getValue());
            }
        } catch (ParseException e) {
//TODO: revise
        }

        return new ParseResult<>(stringBuilder.toString().trim(), pos);
    }

    private ParseResult<String> parseText(String content, int start) throws ParseException {
        String result;
        int newParserPos = parseComments(content, start);
        if (newParserPos > start) {
            return new ParseResult<>("", newParserPos);
        }

        Matcher matcher = textPattern.matcher(content);
        if (matcher.find(start) && matcher.start() == start) {
            result = XmlStrings.encodeSpecialValues(matcher.group("text"));
        } else {
            matcher = cdataPattern.matcher(content);
            if (!matcher.find(start) || matcher.start() != start)
                throw new ParseException("Tag content parsing failed: " + content);
            result =matcher.group("text");
        }

        newParserPos = matcher.start() + matcher.group().length();
        return new ParseResult<>(result, newParserPos);
    }

    @Override
    protected void init() {
        textPattern = Pattern.compile("(?<text>[^<>]+)");
        cdataPattern = Pattern.compile("(<!\\[CDATA\\[){1}(?<text>.*?)(\\]\\]>){1}");
    }

    private int parseComments(String content, int start) {
        final int[] pos = {start};
        commentToken.parseAll(content, start).forEach(c -> pos[0] = c.getParserPos());
        start = pos[0];
        return start;
    }


}
