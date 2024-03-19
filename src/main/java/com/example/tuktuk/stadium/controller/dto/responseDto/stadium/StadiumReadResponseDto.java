package com.example.tuktuk.stadium.controller.dto.responseDto.stadium;

import com.example.tuktuk.stadium.domain.stadium.Stadium;
import lombok.Builder;
import lombok.Getter;


import static com.example.tuktuk.stadium.util.LocationToStringConverter.*;

@Getter
@Builder
public class StadiumReadResponseDto {

    private final long stadiumId;

    private final String name;

    private final String roadAddress;

    private final String specificInfo;

    public static StadiumReadResponseDto from(Stadium stadium) {
        return StadiumReadResponseDto.builder()
                .stadiumId(stadium.getId())
                .name(stadium.getName())
                .roadAddress(convertLocationToString(stadium.getLocation()))
                .specificInfo(stadium.getSpecificInfo())
                .build();
    }
}
