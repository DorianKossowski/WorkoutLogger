package com.zti.workoutLogger.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DateToStringConverter {
    public static String convert(LocalDate date) {
        return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
    }
}
