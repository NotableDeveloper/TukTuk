package com.example.tuktuk.schedule.controller.dto.responseDto;

import com.example.tuktuk.schedule.domain.Schedule;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScheduleSimpleReadResDto {

  private String stadiumWithCourtName;

  private TimeResponseDto timeResponseDto;

  private int participants;

  private String reservationStatus;

  public static ScheduleSimpleReadResDto from(Schedule schedule, String stadiumWithCourtName) {
    return ScheduleSimpleReadResDto.builder()
        .stadiumWithCourtName(stadiumWithCourtName)
        .timeResponseDto(TimeResponseDto.from(schedule.getTime()))
        .participants(schedule.getParticipants().size())
        .reservationStatus(schedule.getReservationStatus().name())
        .build();
  }
}
