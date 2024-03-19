package com.example.tuktuk.stadium.controller.dto.responseDto.stadium;

import com.example.tuktuk.stadium.domain.court.Court;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class CourtReadSimpleResDto {

    private final long courtId;

    private final String courtName;

    private final String courtType;

    private final List<String> images;

    public static CourtReadSimpleResDto from(Court court) {
        List<String> imagePaths = court.getImages().stream().map(
                courtImage -> courtImage.getImagePath()
        ).toList();

        return CourtReadSimpleResDto.builder()
                .courtId(court.getId())
                .courtName(court.getName())
                .courtType(court.getCourtType().toString())
                .images(imagePaths)
                .build();
    }
}
