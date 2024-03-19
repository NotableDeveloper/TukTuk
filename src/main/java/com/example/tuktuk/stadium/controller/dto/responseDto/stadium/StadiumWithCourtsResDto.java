package com.example.tuktuk.stadium.controller.dto.responseDto.stadium;

import com.example.tuktuk.global.page.PageInfo;
import com.example.tuktuk.global.page.PageResponse;
import com.example.tuktuk.stadium.domain.court.Court;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;

@Builder
@Getter
public class StadiumWithCourtsResDto {

    private StadiumReadResponseDto stadium;

    private PageResponse<CourtReadSimpleResDto> courts;

    public static StadiumWithCourtsResDto from(StadiumReadResponseDto stadium,
                                               Page<Court> courtPage) {

        return StadiumWithCourtsResDto.builder()
                .stadium(stadium)
                .courts(new PageResponse<>(
                        courtPage
                                .get()
                                .map(CourtReadSimpleResDto::from)
                                .toList(),
                        PageInfo.from(courtPage)))
                .build();
    }
}
