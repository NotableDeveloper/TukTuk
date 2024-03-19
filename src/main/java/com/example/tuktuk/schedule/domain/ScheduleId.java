package com.example.tuktuk.schedule.domain;

import jakarta.persistence.Column;

import java.io.Serializable;

public class ScheduleId implements Serializable {

    @Column(name = "court_time_slot_id")
    private long id;

    protected ScheduleId() {

    }
}
