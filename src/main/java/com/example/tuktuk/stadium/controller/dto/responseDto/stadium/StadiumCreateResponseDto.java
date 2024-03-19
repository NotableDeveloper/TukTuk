package com.example.tuktuk.stadium.controller.dto.responseDto.stadium;

import com.example.tuktuk.stadium.domain.stadium.Stadium;
import lombok.Builder;
import lombok.Getter;


import static com.example.tuktuk.stadium.util.LocationToStringConverter.convertLocationToString;

@Getter
@Builder
public class StadiumCreateResponseDto {

  private final long stadiumId;

  private final String name;

  private final String roadAddress;

  private final String ownerId;

  private final String specificInfo;

  public static StadiumCreateResponseDto from(Stadium stadium) {
    return StadiumCreateResponseDto.builder()
        .stadiumId(stadium.getId())
        .name(stadium.getName())
        .roadAddress(convertLocationToString(stadium.getLocation()))
        .ownerId(stadium.getOwnerId().getUserId())
        .specificInfo(stadium.getSpecificInfo())
        .build();
  }
}
