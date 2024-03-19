package com.example.tuktuk.security;

import com.example.tuktuk.users.auth.UserInfo;
import com.example.tuktuk.users.auth.UserInfoProvider;
import com.example.tuktuk.users.domain.User;
import com.example.tuktuk.users.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;

import java.io.IOException;
import java.util.List;

@Slf4j
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final UserInfoProvider userInfoProvider;
    private final UserRepository userRepository;

    @Autowired
    AccessControlMap accessControlMap;

    public CustomAuthenticationFilter(UserInfoProvider userInfoProvider, UserRepository userRepository) {
        this.userInfoProvider = userInfoProvider;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*
            회원가입 및 로그인, 조회 요청은 토큰 유효성 검사를 실시하지 않는다.
            단, 구장 소유주 및 일반 회원만이 할 수 있는 조회 요청은 토큰 유효성 검사를 그대로 실시한다.
        */
        if (accessControlMap.checkURI(request.getMethod(), request.getRequestURL().toString())) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("CustomAuthenticationFilter accessed");

        /*
            토큰 유효성 검사 로직
        */
        String accessToken = request.getHeader("Authorization");

        if (accessToken != null && isValidToken(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        else throw new IllegalStateException("유효하지 않은 토큰입니다.");
    }

    private boolean isValidToken(String accessToken) {
        String id = null;
        try {
            List<AttributeType> attributeTypes = userInfoProvider.getUserInfoFromAuthServer(accessToken);
            UserInfo userInfo = new UserInfo(attributeTypes);
            id = userInfo.getId();
        } catch (Exception e) {
            return false;
        }

        User user = userRepository.findById(id).get();
        log.info("user.roles={}",user.getRoles());
        this.saveAuthenticationToSecurityContextHolder(user);

        return true;
    }

    private void saveAuthenticationToSecurityContextHolder(User user) {
        CustomUserDetails userDetails = new CustomUserDetails(user);

        /*
            토큰 유효성 검사를 통과하면 SecurityContextHolder에
            User 정보를 저장한다.
        */
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
