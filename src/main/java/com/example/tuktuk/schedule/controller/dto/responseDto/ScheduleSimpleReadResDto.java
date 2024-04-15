package com.example.tuktuk.schedule.controller.dto.responseDto;

import com.example.tuktuk.schedule.domain.Schedule;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScheduleSimpleReadResDto {

    private long scheduleId;

    private String stadiumWithCourtName;

    private long courtId;

    private TimeResponseDto timeResponseDto;

    private int participants;

    private String reservationStatus;

    public static ScheduleSimpleReadResDto from(Schedule schedule, String stadiumWithCourtName) {
        return ScheduleSimpleReadResDto.builder()
                .scheduleId(schedule.getId())
                .stadiumWithCourtName(stadiumWithCourtName)
                .courtId(schedule.getCourtId().getValue())
                .timeResponseDto(TimeResponseDto.from(schedule.getTime()))
                .participants(schedule.getParticipants().size())
                .reservationStatus(schedule.getReservationStatus().name())
                .build();
    }
}
