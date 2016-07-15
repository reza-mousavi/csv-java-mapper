package com.lector.util.excel.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/12/2016
 */
public class DateTimeUtil {

    public static final DateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-mm-dd");

    public static LocalDate toLocalDate(Date dateCellValue) {
        final Instant instant = dateCellValue.toInstant();
        final ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        return zdt.toLocalDate();
    }

    public static Date toDate(LocalDate value) {
        final ZonedDateTime zonedDateTime = value.atStartOfDay(ZoneId.systemDefault());
        final Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

    public static String toString(Date value) {
        if (value == null) return null;
        return SIMPLE_DATE_FORMAT.format(value);
    }

    public static String toString(LocalDate value) {
        if (value == null) return null;
        final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        return value.format(formatter);
    }

    public static Optional<Date> fromStringDate(String value) {
        if (value == null) return null;
        try {
            final Date date = SIMPLE_DATE_FORMAT.parse(value);
            return Optional.of(date);
        } catch (ParseException e) {
            throw new RuntimeException("Cannot parse string : " + value, e);
        }
    }

    public static Optional<LocalDate> fromStringLocalDate(String value) {
        if (value == null) return null;
        final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        final LocalDate parse = LocalDate.parse(value, formatter);
        return Optional.of(parse);
    }

}
