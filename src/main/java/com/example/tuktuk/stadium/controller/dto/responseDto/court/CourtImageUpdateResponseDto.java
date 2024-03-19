package com.example.tuktuk.stadium.controller.dto.responseDto.court;

import com.example.tuktuk.stadium.domain.court.Court;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CourtImageUpdateResponseDto {

    private final long courtId;

    private final String name;

    private final List<String> images;

    public static CourtImageUpdateResponseDto from(Court court){
        List<String> imagePaths = court.getImages().stream().map(
                courtImage -> courtImage.getImagePath()
        ).toList();

        return CourtImageUpdateResponseDto.builder()
                .courtId(court.getId())
                .name(court.getName())
                .images(imagePaths)
                .build();
    }
}