package com.example.tuktuk.users.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "nick_name", nullable = false, length = 16)
    private String nickName;

    @Column(name = "gender", nullable = false)
    private boolean gender;

    @Column(name = "tel_no", nullable = false, length = 36)
    private String telNo;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Embedded
    private Residence residence;

    @Enumerated(EnumType.STRING)
    @Column(name = "roles", nullable = false)
    private List<Role> roles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", length = 36)
    private Provider provider;

    public String getRolesAsString() {
        return roles.stream()
                .map(Enum::toString)
                .collect(Collectors.joining(", "));
    }

}
