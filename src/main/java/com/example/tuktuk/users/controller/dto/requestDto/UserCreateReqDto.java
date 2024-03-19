package com.example.tuktuk.users.controller.dto.requestDto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateReqDto {

    private String nickName;

    private boolean gender;

    private String telNo;

    private ResidenceReqDto residenceReqDto;
}
