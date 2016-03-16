package com.kudlaienko.parser.engine.xml.parser.tools;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class XmlStringsTest {

    @Test
    public void testEncodeSpecialValuesValid() throws Exception {
        String source = "ABCDEF&lt;";
        String result = XmlStrings.encodeSpecialValues(source);
        String expectedResult = "ABCDEF<";
        assertEquals(expectedResult, result);
    }

    @Test
    public void testEncodeAllSpecialValuesValid() throws Exception {
        String source =  "&lt;" + "&gt;" + "&amp;" + "&apos;" + "&quot;";
        String result = XmlStrings.encodeSpecialValues(source);
        String expectedResult =  "<" + ">" + "&" + "'" + "\"";
        assertEquals(expectedResult, result);
    }

    @Test
    public void testEncodeWithoutRecursiveValid() throws Exception {
        String source =  "&amp;amp;";
        String result = XmlStrings.encodeSpecialValues(source);
        String expectedResult =  "&amp;";
        assertEquals(expectedResult, result);
    }

    @Test (timeout = 500)
    public void testEncodeAllSpecialValuesPerformanceValid() throws Exception {
        String source =  "&lt;" + "&gt;" + "&amp;" + "&apos;" + "&quot;";
        String result = XmlStrings.encodeSpecialValues(source);
        for (int i = 0; i < 1_000_000; i++) {
            XmlStrings.encodeSpecialValues(source);
        }
        String expectedResult =  "<" + ">" + "&" + "'" + "\"";
        assertEquals(expectedResult, result);
    }

}