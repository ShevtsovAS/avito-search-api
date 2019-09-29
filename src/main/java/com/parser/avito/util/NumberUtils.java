package com.parser.avito.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

@UtilityClass
public class NumberUtils {
    public int parseInt(String intString) {
        String trimmed = StringUtils.trimAllWhitespace(intString);
        try {
            return Integer.parseInt(trimmed);
        } catch (NumberFormatException | NullPointerException e) {
            return 0;
        }
    }
}
