package com.example.tuktuk.stadium.domain;

import com.example.tuktuk.global.City;
import com.example.tuktuk.global.Province;
import com.example.tuktuk.stadium.controller.dto.requestDto.LocationReqDto;
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
public class Location {

    @Enumerated(EnumType.STRING)
    @Column(name = "province")
    private Province province;

    @Enumerated(EnumType.STRING)
    @Column(name = "city")
    private City city;

    @Column(name = "road_address")
    private String roadNameAddress;

    public Location(Province province, City city, String roadNameAddress) {
        this.province = province;
        this.city = city;
        this.roadNameAddress = roadNameAddress;
    }

    public static Location of(LocationReqDto reqDto){
        return Location.builder()
                .province(Province.valueOf(reqDto.getProvince()))
                .city(City.valueOf(reqDto.getCity()))
                .roadNameAddress(reqDto.getRoadNameAddress())
                .build();
    }

    public void update(LocationReqDto reqDto){
        this.province = Province.valueOf(reqDto.getProvince());
        this.city = City.valueOf(reqDto.getCity());
        this.roadNameAddress = reqDto.getRoadNameAddress();
    }
}
