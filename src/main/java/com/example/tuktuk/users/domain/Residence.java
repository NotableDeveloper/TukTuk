package com.example.tuktuk.users.domain;

import com.example.tuktuk.global.City;
import com.example.tuktuk.global.Province;
import com.example.tuktuk.users.controller.dto.requestDto.ResidenceReqDto;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Embeddable
@NoArgsConstructor
public class Residence {

    @Enumerated(EnumType.STRING)
    @Column(name = "province")
    private Province province;

    @Enumerated(EnumType.STRING)
    @Column(name = "city")
    private City city;

    public Residence(Province province, City city) {
        this.province = province;
        this.city = city;
    }

    public static Residence of(ResidenceReqDto reqDto){
        return Residence.builder()
                .province(Province.valueOf(reqDto.getProvince()))
                .city(City.valueOf(reqDto.getCity()))
                .build();
    }
}
