package com.parser.avito.util;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.time.Month.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DateTimeUtilTest {

    @Test
    public void parseTodayDate() {
        LocalDateTime actual = DateTimeUtil.parseDate("Сегодня 15:18");
        LocalDateTime expected = LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 18));
        assertThat(actual, is(expected));
    }

    @Test
    public void parseYesterdayDate() {
        LocalDateTime actual = DateTimeUtil.parseDate("Вчера 12:32");
        LocalDateTime expected = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(12, 32));
        assertThat(actual, is(expected));
    }

    @Test
    public void parseJanuaryDate() {
        LocalDateTime actual = DateTimeUtil.parseDate("26 января 11:50");
        LocalDateTime expected = LocalDateTime.of(LocalDate.now().getYear(), JANUARY, 26, 11, 50);
        assertThat(actual, is(expected));
    }

    @Test
    public void parseFebruaryDate() {
        LocalDateTime actual = DateTimeUtil.parseDate("26 февраля 11:50");
        LocalDateTime expected = LocalDateTime.of(LocalDate.now().getYear(), FEBRUARY, 26, 11, 50);
        assertThat(actual, is(expected));
    }

    @Test
    public void parseMarchDate() {
        LocalDateTime actual = DateTimeUtil.parseDate("26 марта 11:50");
        LocalDateTime expected = LocalDateTime.of(LocalDate.now().getYear(), MARCH, 26, 11, 50);
        assertThat(actual, is(expected));
    }

    @Test
    public void parseAprilDate() {
        LocalDateTime actual = DateTimeUtil.parseDate("26 апреля 11:50");
        LocalDateTime expected = LocalDateTime.of(LocalDate.now().getYear(), APRIL, 26, 11, 50);
        assertThat(actual, is(expected));
    }

    @Test
    public void parseMayDate() {
        LocalDateTime actual = DateTimeUtil.parseDate("26 мая 11:50");
        LocalDateTime expected = LocalDateTime.of(LocalDate.now().getYear(), MAY, 26, 11, 50);
        assertThat(actual, is(expected));
    }

    @Test
    public void parseJuneDate() {
        LocalDateTime actual = DateTimeUtil.parseDate("26 июня 11:50");
        LocalDateTime expected = LocalDateTime.of(LocalDate.now().getYear(), JUNE, 26, 11, 50);
        assertThat(actual, is(expected));
    }

    @Test
    public void parseJulyDate() {
        LocalDateTime actual = DateTimeUtil.parseDate("26 июля 11:50");
        LocalDateTime expected = LocalDateTime.of(LocalDate.now().getYear(), JULY, 26, 11, 50);
        assertThat(actual, is(expected));
    }

    @Test
    public void parseAugustDate() {
        LocalDateTime actual = DateTimeUtil.parseDate("26 августа 11:50");
        LocalDateTime expected = LocalDateTime.of(LocalDate.now().getYear(), AUGUST, 26, 11, 50);
        assertThat(actual, is(expected));
    }

    @Test
    public void parseSeptemberDate() {
        LocalDateTime actual = DateTimeUtil.parseDate("26 сентября 11:50");
        LocalDateTime expected = LocalDateTime.of(LocalDate.now().getYear(), SEPTEMBER, 26, 11, 50);
        assertThat(actual, is(expected));
    }

    @Test
    public void parseOctoberDate() {
        LocalDateTime actual = DateTimeUtil.parseDate("26 октября 11:50");
        LocalDateTime expected = LocalDateTime.of(LocalDate.now().getYear(), OCTOBER, 26, 11, 50);
        assertThat(actual, is(expected));
    }

    @Test
    public void parseNovemberDate() {
        LocalDateTime actual = DateTimeUtil.parseDate("26 ноября 11:50");
        LocalDateTime expected = LocalDateTime.of(LocalDate.now().getYear(), NOVEMBER, 26, 11, 50);
        assertThat(actual, is(expected));
    }

    @Test
    public void parseDecemberDate() {
        LocalDateTime actual = DateTimeUtil.parseDate("26 декабря 11:50");
        LocalDateTime expected = LocalDateTime.of(LocalDate.now().getYear(), DECEMBER, 26, 11, 50);
        assertThat(actual, is(expected));
    }
}