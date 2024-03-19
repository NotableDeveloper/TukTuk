package com.example.tuktuk.stadium.controller.dto.requestDto.stadium;

import com.example.tuktuk.stadium.controller.dto.requestDto.LocationReqDto;
import lombok.Getter;

@Getter
public class StadiumUpdateRequestDto {

    private String name;

    private LocationReqDto locationReqDto;

    private String specificInfo;

}
