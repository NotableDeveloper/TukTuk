package com.example.tuktuk.schedule.domain;

import com.example.tuktuk.schedule.controller.dto.requestDto.ScheduleUpdateReqDto;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Setter;

@Getter
@Builder
@Embeddable
public class Time {

    protected Time() {
    }

    public Time(LocalDate playDate, LocalTime startTime, LocalTime endTime) {
        this.playDate = playDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Column(name = "play_date")
    private LocalDate playDate;

    @Column(name = "start_time", columnDefinition = "TIME")
    private LocalTime startTime;

    @Column(name = "end_time", columnDefinition = "TIME")
    private LocalTime endTime;

    public void update(ScheduleUpdateReqDto request){
        this.playDate = request.getPlayDate();
        this.startTime = request.getStartTime();
        this.endTime = request.getEndTime();
    }
}
