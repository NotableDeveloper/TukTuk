package com.example.tuktuk.users.controller.dto.responseDto;


import com.example.tuktuk.users.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserReadResDto {

    private final String nickName;

    private final ResidenceResDto residenceResDto;

    private final String role;

    private final String provider;

    public static UserReadResDto from(User user) {
        return UserReadResDto.builder()
                .nickName(user.getNickName())
                .residenceResDto(ResidenceResDto.from(user.getResidence()))
                .role("USER")
                .provider(user.getProvider().name())
                .build();
    }
}
