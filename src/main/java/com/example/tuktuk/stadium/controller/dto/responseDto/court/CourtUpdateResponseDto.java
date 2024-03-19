package com.example.tuktuk.stadium.controller.dto.responseDto.court;

import com.example.tuktuk.stadium.domain.court.Court;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CourtUpdateResponseDto {

    private final long courtId;

    private final String name;

    private final String courtType;

    private final int hourlyRentFee;

    private final List<String> images;

    public static CourtUpdateResponseDto from(Court court){
        List<String> imagePaths = court.getImages().stream().map(
                courtImage -> courtImage.getImagePath()
        ).toList();

        return CourtUpdateResponseDto.builder()
                .courtId(court.getId())
                .name(court.getName())
                .courtType(court.getCourtType().name())
                .hourlyRentFee(court.getHourlyRentFee())
                .images(imagePaths)
                .build();
    }
}
