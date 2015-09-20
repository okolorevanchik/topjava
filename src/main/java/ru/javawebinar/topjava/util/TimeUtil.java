package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * GKislin
 * 07.01.2015.
 */
public class TimeUtil {
    public static final DateTimeFormatter DATE_TME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static boolean isBetween(LocalDate ld, LocalDate startDate, LocalDate endDate) {
        return startDate.compareTo(ld) <= 0 && endDate.compareTo(ld) >= 0;
    }

    public static boolean isBetween(LocalDateTime ldt, LocalDateTime start, LocalDateTime end) {
        boolean dateCompare = isBetween(ldt.toLocalDate(), start.toLocalDate(), end.toLocalDate());
        boolean timeCompare = isBetween(ldt.toLocalTime(), start.toLocalTime(), end.toLocalTime());
        return dateCompare && timeCompare;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TME_FORMATTER);
    }
}
