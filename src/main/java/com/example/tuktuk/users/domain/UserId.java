package com.example.tuktuk.users.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserId implements Serializable {

    @Column(name = "user_id", nullable = false, length = 255)
    private String userId;

    public UserId(String id) {
        this.userId = id;
    }
}
