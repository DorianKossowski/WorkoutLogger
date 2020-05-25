package com.zti.workoutLogger.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DateToStringConverter {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

    public static String convert(LocalDate date) {
        return date.format(FORMATTER);
    }
}
