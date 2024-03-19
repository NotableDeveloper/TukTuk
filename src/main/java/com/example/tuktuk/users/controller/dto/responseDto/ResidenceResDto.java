package com.example.tuktuk.users.controller.dto.responseDto;

import com.example.tuktuk.users.domain.Residence;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResidenceResDto {

    private final String province;

    private final String city;

    public static ResidenceResDto from(final Residence residence) {

        return ResidenceResDto.builder()
                .province(residence.getProvince().name())
                .city(residence.getCity().name())
                .build();
    }
}
