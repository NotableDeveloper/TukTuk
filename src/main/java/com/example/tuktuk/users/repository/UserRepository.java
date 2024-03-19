package com.example.tuktuk.users.repository;

import com.example.tuktuk.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE u.id = :userId")
    public Optional<User> findById(@Param("userId") String userId);
}
