package com.example.tuktuk.schedule.controller.dto.responseDto;

import com.example.tuktuk.schedule.domain.Schedule;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MatchEnrollResponseDto {

    private long scheduleId;

    private long courtId;

    private TimeResponseDto time;

    private int matchFee;

    public static MatchEnrollResponseDto from(Schedule schedule) {
        return MatchEnrollResponseDto.builder()
                .scheduleId(schedule.getId())
                .courtId(schedule.getCourtId().getValue())
                .time(TimeResponseDto.from(schedule.getTime()))
                .matchFee(schedule.getMatchRegularFee().getValue())
                .build();
    }
}
