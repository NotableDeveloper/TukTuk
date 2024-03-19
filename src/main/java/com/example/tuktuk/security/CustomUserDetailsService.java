package com.example.tuktuk.security;

import com.example.tuktuk.users.domain.User;
import com.example.tuktuk.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * userName이 id이다.
     */

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isPresent()) {
            return new CustomUserDetails(userOpt.get());
        }

        throw new UsernameNotFoundException("로그인 정보가 올바르지 않습니다.");
    }
}
