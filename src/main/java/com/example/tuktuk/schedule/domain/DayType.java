package com.example.tuktuk.schedule.domain;

public enum DayType {

    WEEKDAY("평일"),
    WEEKEND("주말");

    private final String value;

    DayType(String value) {
        this.value = value;
    }
}
