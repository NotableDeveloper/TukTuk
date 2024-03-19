package com.example.tuktuk.security;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccessControlRecord {

    private String httpMethod;

    private String url;

    public static AccessControlRecord from(String httpMethod, String url){
        return AccessControlRecord.builder()
                .httpMethod(httpMethod)
                .url(url)
                .build();
    }
}
