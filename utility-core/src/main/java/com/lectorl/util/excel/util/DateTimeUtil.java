package com.lectorl.util.excel.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/12/2016
 */
public class DateTimeUtil {

    public static LocalDate toLocalDate(Date dateCellValue) {
        final Date input = dateCellValue;
        final Instant instant = input.toInstant();
        final ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        return zdt.toLocalDate();
    }

    public static Date toDate(LocalDate value) {
        final ZonedDateTime zonedDateTime = value.atStartOfDay(ZoneId.systemDefault());
        final Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

}
