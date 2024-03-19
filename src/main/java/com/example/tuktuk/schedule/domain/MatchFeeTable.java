package com.example.tuktuk.schedule.domain;

import com.example.tuktuk.global.Province;

import static com.example.tuktuk.schedule.domain.DayType.*;
import static com.example.tuktuk.global.Province.*;
import static com.example.tuktuk.global.Province.SEOUL;

public enum MatchFeeTable {

    SEOUL_WEEK(SEOUL, WEEKDAY, 6000),
    SEOUL_WEEKEND(SEOUL, WEEKEND, 7000),
    GYEONGGI_WEEK(GYEONGGI, WEEKDAY, 5000),
    GYEONGGI_WEEKEND(GYEONGGI, WEEKEND, 6000);

    private final Province province;
    private final DayType dayType;
    private final int fee;

    MatchFeeTable(Province province, DayType dayType, int fee) {
        this.province = province;
        this.dayType = dayType;
        this.fee = fee;
    }

    public static int findFee(Province province,DayType dayType){
        for(MatchFeeTable matchFee: values()){
            if(matchFee.province.equals(province) && matchFee.dayType.equals(dayType)){
                return matchFee.fee;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 가격표입니다.");
    }
}
