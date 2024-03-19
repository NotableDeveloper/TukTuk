package com.example.tuktuk.global;

public enum Province {
    SEOUL("서울"),
    GYEONGGI("경기"),
    INCHEON("인천"),
    GANGWON("강원"),
    DAEJEON("대전"),
    SEJONG("세종"),
    CHUNGNAM("충남"),
    CHUNGBUK("충북"),
    DAEGU("대구"),
    GYEONGBUK("경북"),
    GYEONGNAM("경남"),
    BUSAN("부산"),
    ULSAL("울산"),
    GWANGJU("광주"),
    JEONNAM("전남"),
    JEONBUK("전북"),
    JEJU("제주");

    private final String province;

    Province(String province) {
        this.province = province;
    }
}
