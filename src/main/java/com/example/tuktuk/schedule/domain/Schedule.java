package com.example.tuktuk.schedule.domain;

import com.example.tuktuk.global.Money;
import com.example.tuktuk.schedule.controller.dto.requestDto.ScheduleUpdateReqDto;
import com.example.tuktuk.stadium.domain.court.CourtId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schedules")
public class Schedule {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CourtId courtId;

    @Embedded
    private Time time;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "participants",
            joinColumns = @JoinColumn(name = "id"))
    @OrderColumn(name = "participant_idx")
    private List<Participant> participants = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_status", nullable = false)
    private ReservationStatus reservationStatus;

    @Embedded
    private Money matchRegularFee;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    public void update(ScheduleUpdateReqDto reqDto){
        this.type = Type.valueOf(reqDto.getType());
        this.time.update(reqDto);
    }

    public void delete(){
        this.isDeleted=true;
    }
}
