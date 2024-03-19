package com.example.tuktuk.schedule.controller.dto.requestDto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScheduleUpdateReqDto {

    private String type;

    private LocalDate playDate;

    private LocalTime startTime;

    private LocalTime endTime;
}
