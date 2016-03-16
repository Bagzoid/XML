package com.kudlaienko.parser.engine.xml.parser;

import com.kudlaienko.parser.engine.xml.parser.tokens.CustomToken;
import com.kudlaienko.parser.engine.xml.parser.tools.XmlStrings;
import com.kudlaienko.parser.engine.xml.property.Attribute;
import com.kudlaienko.parser.shell.exceptions.ParseException;
import com.kudlaienko.parser.shell.ParseResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final public class AttributeParser extends CustomToken<String, Attribute> {
    private Pattern pattern;

    @Override
    public ParseResult<Attribute> doParse(String content, int start) throws ParseException {
        Matcher matcher = pattern.matcher(content);
        if (!matcher.find(start) || matcher.start() != start)
            throw new ParseException("Property parsing failed at char " + start);

        int parserPos = matcher.start() + matcher.group().length();
        String name = matcher.group(1);
        String value = XmlStrings.encodeSpecialValues(matcher.group(2));

        return new ParseResult<>(new Attribute(name, value), parserPos);
    }

    @Override
    protected void init() {
        pattern = Pattern.compile("[\\s]*([A-Za-z_0-9]+)[\\s]*=[\\s]*[\"]([A-Za-z_0-9\\.\\s;&=]*?)[\"][\\s]*");
    }
}
