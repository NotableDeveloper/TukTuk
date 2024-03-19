package com.example.tuktuk.schedule.controller.dto.responseDto;

import com.example.tuktuk.schedule.domain.Schedule;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class ScheduleCreateResDto {

  private final long courtId;

  private final LocalDate playDate;

  private final LocalTime startTime;

  private final LocalTime endTime;

  private final String type;

  private final int matchRegularFee;

  private final int hourlyRentFee;

  public static ScheduleCreateResDto from(Schedule courtTimeSlot, int hourlyRentFee) {
    return ScheduleCreateResDto.builder()
        .courtId(courtTimeSlot.getCourtId().getValue())
        .playDate(courtTimeSlot.getTime().getPlayDate())
        .startTime(courtTimeSlot.getTime().getStartTime())
        .endTime(courtTimeSlot.getTime().getEndTime())
        .type(courtTimeSlot.getType().toString())
        .matchRegularFee(courtTimeSlot.getMatchRegularFee().getValue())
        .hourlyRentFee(hourlyRentFee)
        .build();
  }

}
