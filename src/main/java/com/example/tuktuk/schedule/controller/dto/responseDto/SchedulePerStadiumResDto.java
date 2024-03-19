package com.example.tuktuk.schedule.controller.dto.responseDto;

import com.example.tuktuk.global.page.PageInfo;
import com.example.tuktuk.stadium.controller.dto.responseDto.stadium.StadiumSimpleReadResDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class SchedulePerStadiumResDto {

  private StadiumSimpleReadResDto stadium;

  private List<ScheduleReadResponseDto> schedules;

  private PageInfo pageInfo;

  public static SchedulePerStadiumResDto from(StadiumSimpleReadResDto stadium,
      List<ScheduleReadResponseDto> schedules, PageInfo pageInfo) {

    return SchedulePerStadiumResDto.builder()
        .stadium(stadium)
        .schedules(schedules)
        .pageInfo(pageInfo)
        .build();
  }
}