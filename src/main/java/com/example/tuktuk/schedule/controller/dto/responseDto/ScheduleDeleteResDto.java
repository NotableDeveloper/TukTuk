package com.example.tuktuk.schedule.controller.dto.responseDto;

import com.example.tuktuk.schedule.domain.Schedule;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScheduleDeleteResDto {

    private long scheduleId;

    private long courtId;

    private TimeResponseDto timeResponseDto;

    private String type;

    public static ScheduleDeleteResDto from(Schedule schedule) {
        return ScheduleDeleteResDto.builder()
                .scheduleId(schedule.getId())
                .courtId(schedule.getCourtId().getValue())
                .timeResponseDto(TimeResponseDto.from(schedule.getTime()))
                .type(schedule.getType().name())
                .build();
    }
}
