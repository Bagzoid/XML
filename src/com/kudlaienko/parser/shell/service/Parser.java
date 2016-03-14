package com.kudlaienko.parser.shell.service;

import com.kudlaienko.parser.shell.exceptions.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/***
 * Parser for specified content and specified result
 *
 * @param <C> content to be parsed
 * @param <R> result to be returned
 */
public abstract class Parser<C, R> {
    private boolean isInitiated = false;

    /***
     * Parse data for one token
     *
     * @param content to be parsed
     * @param start   position
     * @return ParseResult
     * @throws ParseException
     */
    final public ParseResult<R> parse(C content, int start) throws ParseException {
        if (!isInitiated) {
            isInitiated = true;
            init();
        }
        return doParse(content, start);
    }

    /***
     * Parse data for one token
     *
     * @param content to be parsed
     * @return ParseResult
     * @throws ParseException
     */
    final public ParseResult<R> parse(C content) throws ParseException {
        return parse(content, 0);
    }

    /***
     * Check if content can be parsed and consuming for parse result
     *
     * @param content  to be checked
     * @param start    position
     * @param consumer for successfully parsed data
     * @return
     */
    final public boolean isParsable(C content, int start, Consumer<ParseResult<R>> consumer) {
        try {
            ParseResult<R> parseResult = parse(content, start);
            if (consumer != null) {
                consumer.accept(parseResult);
            }
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    /***
     * Parse data for all tokens and consuming for each parse result
     *
     * @param content  to be parsed
     * @param start    position
     * @param consumer for parsed data
     */
    final public void forEachParsed(C content, int start, Consumer<ParseResult<R>> consumer) {
        final Integer[] newPos = {start};
        while (isParsable(content, newPos[0], parseResult -> {
            consumer.accept(parseResult);
            newPos[0] = parseResult.getParserPos();
        })) ;
    }

    /***
     * Parse data for all tokens
     *
     * @param content
     * @param start
     * @return
     */
    final public List<ParseResult<R>> parseAll(C content, int start) {
        List<ParseResult<R>> result = new ArrayList<>();
        forEachParsed(content, start, (r) -> result.add(r));
        return result;
    }

    /***
     * Parsing implementation for one token
     *
     * @param content Content to be parsed
     * @param start   Start position
     * @return ParseResult
     * @throws ParseException in case parse failure
     */
    protected abstract ParseResult<R> doParse(C content, int start) throws ParseException;

    /***
     * Initialization before parsing
     */
    protected abstract void init();

}
