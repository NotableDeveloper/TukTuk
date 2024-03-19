package com.example.tuktuk.stadium.controller.dto.responseDto.stadium;

import com.example.tuktuk.global.page.PageInfo;
import com.example.tuktuk.stadium.domain.stadium.Stadium;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StadiumSimpleReadResDto {

  private Long stadiumId;

  private String stadiumName;

  private String roadNameAddress;

  public static StadiumSimpleReadResDto from(Stadium stadium){
    return StadiumSimpleReadResDto.builder()
        .stadiumId(stadium.getId())
        .stadiumName(stadium.getName())
        .roadNameAddress(stadium.getLocation().getRoadNameAddress())
        .build();
  }
}
