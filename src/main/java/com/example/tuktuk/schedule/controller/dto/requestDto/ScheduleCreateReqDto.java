package com.example.tuktuk.schedule.controller.dto.requestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class ScheduleCreateReqDto {

    private long courtId;

    private LocalDate playDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private String type;
}







