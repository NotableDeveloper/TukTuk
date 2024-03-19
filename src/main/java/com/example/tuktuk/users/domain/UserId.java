package com.example.tuktuk.users.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserId implements Serializable {

    @Column(name = "user_id", nullable = false, length = 255)
    private String userId;

    public UserId(String sub) {
        this.userId = sub;
    }

    public String getUserId() {
        return this.userId;
    }
}
