package com.example.tuktuk.stadium.controller.dto.requestDto.court;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
public class CourtUpdateRequestDto {

    private String name;

    private String courtType;

    private int hourlyRentFee;
}
