package com.example.tuktuk.schedule.domain;

import com.example.tuktuk.users.domain.UserId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participant {

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "id", column = @Column(name = "participant_id"))
    )
    private UserId userId;

    public Participant(String id){
        this.userId = new UserId(id);
    }
}
