package com.example.tuktuk.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class SecurityContextHolderUtil {

    public static String getUserId() throws RuntimeException{
        try {
            CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return principal.getUser().getId();
        } catch (Exception e){
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
    }

    public static String getUsername() {
        CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUsername();
    }

    public static Collection<? extends GrantedAuthority> getAuthorities() {
        CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getAuthorities();
    }
}
