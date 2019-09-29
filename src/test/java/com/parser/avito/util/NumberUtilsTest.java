package com.parser.avito.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NumberUtilsTest {

    @Test
    public void parseNumberWithWhiteSpaces() {
        int actual = NumberUtils.parseInt(" 3 869  ");
        int expected = 3869;
        assertEquals(actual, expected);
    }

    @Test
    public void parseNotNumber() {
        int actual = NumberUtils.parseInt("not number");
        int expected = 0;
        assertEquals(actual, expected);
    }

    @Test
    public void parseNull() {
        int actual = NumberUtils.parseInt(null);
        int expected = 0;
        assertEquals(actual, expected);
    }
}