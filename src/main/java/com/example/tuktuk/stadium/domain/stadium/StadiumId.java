package com.example.tuktuk.stadium.domain.stadium;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StadiumId implements Serializable {

    @Column(name = "stadium_id")
    private long id;

    public StadiumId(long id) {
        this.id = id;
    }
}
