package com.example.tuktuk.stadium.domain.court;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourtId implements Serializable {

    @Column(name = "court_id")
    private long id;

    public CourtId(long id) {
        this.id = id;
    }

    public long getValue(){
        return id;
    }
}
