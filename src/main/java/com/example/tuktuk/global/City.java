package com.example.tuktuk.global;

import static com.example.tuktuk.global.Province.SEOUL;

public enum City {
    JONGNO(SEOUL, "종로구"),
    JUNGGU(SEOUL, "중구"),
    YONGSAN(SEOUL, "용산구"),
    SEONGDONG(SEOUL, "성동구"),
    GWANGJIN(SEOUL, "광진구"),
    DONGDAEMUN(SEOUL, "동대문구"),
    JUNGNANG(SEOUL, "중랑구"),
    SEONGBUK(SEOUL, "성북구"),
    GANGBUK(SEOUL, "강북구"),
    DOBONG(SEOUL, "도봉구"),
    NOWON(SEOUL, "노원구"),
    EUNPYEONG(SEOUL, "은평구"),
    SEODAEMUN(SEOUL, "서대문구"),
    MAPO(SEOUL, "마포구"),
    YANGCHEON(SEOUL, "양천구"),
    GANGSEO(SEOUL, "강서구"),
    GURO(SEOUL, "구로구"),
    GEUMCHEON(SEOUL, "금천구"),
    YEONGDEUNGPO(SEOUL, "영등포구"),
    DONGJAK(SEOUL, "동작구"),
    GWANAK(SEOUL, "관악구"),
    SEOCHO(SEOUL, "서초구"),
    GANGNAM(SEOUL, "강남구"),
    SONGPA(SEOUL, "송파구"),
    GANGDONG(SEOUL, "강동구");


    private final Province province;
    private final String city;

    City(Province province, String city) {
        this.province = province;
        this.city = city;
    }
}
