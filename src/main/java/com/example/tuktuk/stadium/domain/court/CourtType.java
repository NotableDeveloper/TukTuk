package com.example.tuktuk.stadium.domain.court;

import lombok.Getter;

@Getter
public enum CourtType {

    FIVE_FIVE(10,15),
    SIX_SIX(10,18);

    private final int minParticipants;
    private final int maxParticipants;

    CourtType(int minParticipants, int maxParticipants) {
        this.minParticipants = minParticipants;
        this.maxParticipants = maxParticipants;
    }
}

