package com.example.tuktuk.schedule.domain;

import com.example.tuktuk.schedule.util.DateUtils;
import com.example.tuktuk.global.Province;

import java.time.LocalDate;

public class MatchRegularFeeManager {

    private MatchRegularFeeManager() {
    }

    public static int calculateRegularFee(Province province, LocalDate localDate) {
        if (DateUtils.isWeekDay(localDate)) {
            return MatchFeeTable.findFee(province, DayType.WEEKDAY);
        }
        return MatchFeeTable.findFee(province, DayType.WEEKEND);
    }

}
