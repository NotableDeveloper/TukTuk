package com.example.tuktuk.users.auth;

import lombok.Getter;

@Getter
public class TokenInfo {

    String id_token;

    String access_token;

    String refresh_token;

    String token_type;

    String expires_in;
}
