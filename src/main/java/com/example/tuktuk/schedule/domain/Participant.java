package com.example.tuktuk.schedule.domain;

import com.example.tuktuk.users.domain.UserId;
import jakarta.persistence.*;

@Embeddable
public class Participant {

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "id", column = @Column(name = "participant_id"))
    )
    private UserId userId;
}
