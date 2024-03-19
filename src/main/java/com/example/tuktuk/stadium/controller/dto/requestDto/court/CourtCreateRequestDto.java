package com.example.tuktuk.stadium.controller.dto.requestDto.court;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CourtCreateRequestDto {

    private Long stadiumId;

    private String name;

    private String courtType;

    private int hourlyRentFee;
}
