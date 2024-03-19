package com.example.tuktuk.stadium.controller.dto.responseDto.court;

import com.example.tuktuk.stadium.domain.court.Court;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CourtDeleteResponseDto {

    private final long courtId;

    private final String courtName;

    private final String stadiumName;

    private final String courtType;

    private int hourlyRentFee;

    private List<String> images;

    public static CourtDeleteResponseDto from(Court court) {
        List<String> imagePaths = court.getImages().stream().map(
                courtImage -> courtImage.getImagePath()
        ).toList();

        return CourtDeleteResponseDto.builder()
                .courtId(court.getId())
                .courtName(court.getName())
                .stadiumName(court.getStadium().getName())
                .courtType(court.getCourtType().name())
                .hourlyRentFee(court.getHourlyRentFee())
                .images(imagePaths)
                .build();
    }
}
