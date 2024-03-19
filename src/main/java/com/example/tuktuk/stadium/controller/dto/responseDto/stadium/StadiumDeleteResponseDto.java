package com.example.tuktuk.stadium.controller.dto.responseDto.stadium;

import static com.example.tuktuk.stadium.util.LocationToStringConverter.convertLocationToString;

import com.example.tuktuk.stadium.domain.stadium.Stadium;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StadiumDeleteResponseDto {

  private final long stadiumId;

  private final String name;

  private final String ownerId;

  private final String roadAddress;

  public static StadiumDeleteResponseDto from(Stadium stadium) {
    return StadiumDeleteResponseDto.builder()
        .stadiumId(stadium.getId())
        .name(stadium.getName())
        .ownerId(stadium.getOwnerId().getUserId())
        .roadAddress(convertLocationToString(stadium.getLocation()))
        .build();
  }
}

