package com.example.tuktuk.stadium.controller.dto.requestDto.court;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CourtImageUpdateRequestDto {

    List<String> imagePaths;
}
