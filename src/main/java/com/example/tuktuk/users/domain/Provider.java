package com.example.tuktuk.users.domain;

public enum Provider {

    KAKAO("카카오"),
    GOOGLE("구글"),
    NAVER("네이버"),
    REGULAR("일반");
    private String name;

    Provider(String name) {
        this.name = name;
    }

    public static Provider fromName(String name){
        if(name.equals("KakaoLogin")){
            return Provider.KAKAO;
        }
        if(name.equals("NaverLogin")){
            return Provider.NAVER;
        }
        if(name.equals("GoogleLogin")){
            return Provider.GOOGLE;
        }
        return Provider.REGULAR;
    }
}
