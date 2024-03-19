package com.example.tuktuk.schedule.util;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateUtils {

    private DateUtils() {
    }

    public static boolean isWeekDay(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

    public static boolean isWeekEnd(LocalDate date) {
        return !isWeekDay(date);
    }
}
