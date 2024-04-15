package com.example.tuktuk.schedule.controller.dto.responseDto;

import com.example.tuktuk.schedule.domain.Schedule;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class ScheduleUpdateResDto {

    private long scheduleId;

    private final long courtId;

    private final LocalDate playDate;

    private final LocalTime startTime;

    private final LocalTime endTime;

    private final String type;

    private final int matchRegularFee;

    private final int hourlyRentFee;

    public static ScheduleUpdateResDto from(Schedule schedule, int hourlyRentFee) {
        return ScheduleUpdateResDto.builder()
                .scheduleId(schedule.getId())
                .courtId(schedule.getCourtId().getValue())
                .playDate(schedule.getTime().getPlayDate())
                .startTime(schedule.getTime().getStartTime())
                .endTime(schedule.getTime().getEndTime())
                .type(schedule.getType().toString())
                .matchRegularFee(schedule.getMatchRegularFee().getValue())
                .hourlyRentFee(hourlyRentFee)
                .build();
    }
}
