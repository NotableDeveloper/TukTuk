package com.example.tuktuk.stadium.repository;

import com.example.tuktuk.global.Province;
import com.example.tuktuk.stadium.domain.stadium.Stadium;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StadiumRepository extends JpaRepository<Stadium, Long> {

    public Optional<Stadium> findById(Long id);

    @Query("SELECT s FROM Stadium s WHERE s.ownerId.userId = :id")
    public Page<Stadium> findByOwnerId(String id, Pageable page);

    @Query("SELECT s FROM Stadium s WHERE s.ownerId.userId = :ownerId AND s.id = :stadiumId")
    public Stadium findByOwnerIdAndStadiumId(String ownerId, long stadiumId);

    @Query("SELECT s FROM Stadium s WHERE s.location.province = :province")
    public List<Stadium> findByProvince(Province province);

    @Query("SELECT s FROM Stadium s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    public Page<Stadium> findByKeyword(@Param("keyword") String keyword, Pageable page);
}
