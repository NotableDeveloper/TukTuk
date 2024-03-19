package com.example.tuktuk.users.domain;

public enum Role {

    USER("사용자"),
    FIELD_OWNER("구장주"),
    ADMIN("관리자");
    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

}
