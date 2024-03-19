package com.example.tuktuk.schedule.controller.dto.responseDto;

import com.example.tuktuk.schedule.domain.Time;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class TimeResponseDto {

    private LocalDate playDate;

    private LocalTime startTime;

    private LocalTime endTime;

    public static TimeResponseDto from(Time time) {
        return TimeResponseDto.builder()
                .playDate(time.getPlayDate())
                .startTime(time.getStartTime())
                .endTime(time.getEndTime())
                .build();
    }
}
