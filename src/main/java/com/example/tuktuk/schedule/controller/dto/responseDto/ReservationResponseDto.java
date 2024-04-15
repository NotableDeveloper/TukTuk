package com.example.tuktuk.schedule.controller.dto.responseDto;

import com.example.tuktuk.schedule.domain.Schedule;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationResponseDto {

  private long scheduleId;

  private long courtId;

  private TimeResponseDto time;

  private int rentFee;

  public static ReservationResponseDto from(Schedule schedule, int rentFee) {
    return ReservationResponseDto.builder()
        .scheduleId(schedule.getId())
        .courtId(schedule.getCourtId().getValue())
        .time(TimeResponseDto.from(schedule.getTime()))
        .rentFee(rentFee)
        .build();
  }
}
