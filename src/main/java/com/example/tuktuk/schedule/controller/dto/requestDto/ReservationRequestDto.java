package com.example.tuktuk.schedule.controller.dto.requestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationRequestDto {

  private long scheduleId;

  private int participants;
}
