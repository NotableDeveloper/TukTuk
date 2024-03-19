package com.example.tuktuk.users.service;


import com.example.tuktuk.users.auth.*;
import com.example.tuktuk.users.controller.dto.responseDto.UserReadResDto;
import com.example.tuktuk.users.domain.User;
import com.example.tuktuk.users.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final UserRepository userRepository;

    private final TokenProvider tokenProvider;

    private final UserInfoProvider userInfoProvider;

    public UserReadResDto login(String code, HttpServletResponse response) {
        TokenInfo tokenInfo = tokenProvider.getToken(code);
        String accessToken = tokenInfo.getAccess_token();
        List<AttributeType> attributeTypes = userInfoProvider.getUserInfoFromAuthServer(accessToken);
        UserInfo userInfo = new UserInfo(attributeTypes);

        String id = userInfo.getId();
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("회원가입 되지 않은 사용자입니다."));

        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        return UserReadResDto.from(user);
    }

    public UserInfo createUser(String code) {
        TokenInfo tokenInfo = tokenProvider.getToken(code);
        String accessToken = tokenInfo.getAccess_token();
        List<AttributeType> attributeTypes = userInfoProvider.getUserInfoFromAuthServer(accessToken);
        return new UserInfo(attributeTypes);
    }
}
