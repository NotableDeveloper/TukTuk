package com.example.tuktuk.reservation;

import com.example.tuktuk.global.Money;
import com.example.tuktuk.schedule.domain.ScheduleId;
import com.example.tuktuk.users.domain.UserId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservations")
public class Reservation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "userId", column = @Column(name = "member_id"))
    private UserId memberId;

    @Embedded
    private ScheduleId sliceId;

    @Embedded
    private Money fee;
}
