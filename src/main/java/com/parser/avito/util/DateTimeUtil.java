package com.parser.avito.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.Month.*;
import static java.util.AbstractMap.SimpleEntry;

@UtilityClass
public class DateTimeUtil {
    private static final String TODAY = "сегодня";
    private static final String YESTERDAY = "вчера";
    private static final Map<String, Month> monthMap = Stream.of(
            new SimpleEntry<>("января", JANUARY),
            new SimpleEntry<>("февраля", FEBRUARY),
            new SimpleEntry<>("марта", MARCH),
            new SimpleEntry<>("апреля", APRIL),
            new SimpleEntry<>("мая", MAY),
            new SimpleEntry<>("июня", JUNE),
            new SimpleEntry<>("июля", JULY),
            new SimpleEntry<>("августа", AUGUST),
            new SimpleEntry<>("сентября", SEPTEMBER),
            new SimpleEntry<>("октября", OCTOBER),
            new SimpleEntry<>("ноября", NOVEMBER),
            new SimpleEntry<>("декабря", DECEMBER)
    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    public static LocalDateTime parseDate(String date) {
        date = StringUtils.trimToEmpty(date).toLowerCase();
        String[] dateTimeParts = date.split("[\\u00a0,\\s]");

        switch (dateTimeParts.length) {
            case 2:
                return getDateTime(dateTimeParts[0], dateTimeParts[1]);
            case 3:
                return getDateTime(dateTimeParts[0], dateTimeParts[1], dateTimeParts[2]);
            default:
                return null;
        }
    }

    private static LocalDateTime getDateTime(String strDate, String strTime) {
        LocalTime time = LocalTime.parse(strTime);
        switch (strDate) {
            case YESTERDAY:
                return LocalDateTime.of(LocalDate.now().minusDays(1), time);
            case TODAY:
            default:
                return LocalDateTime.of(LocalDate.now(), time);
        }
    }

    private static LocalDateTime getDateTime(String strDay, String strMonth, String strTime) {
        int year = LocalDate.now().getYear();
        int day = NumberUtils.parseInt(strDay);
        Month month = monthMap.get(strMonth);
        LocalTime time = LocalTime.parse(strTime);
        return LocalDateTime.of(LocalDate.of(year, month, day), time);
    }
}
