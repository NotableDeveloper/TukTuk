package com.example.tuktuk.stadium.repository;
import com.example.tuktuk.stadium.domain.court.Court;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourtRepository extends JpaRepository<Court, Long> {
    public Optional<Court> findById(Long id);

    @Query("SELECT c FROM Court c WHERE c.stadium.id = :stadiumId")
    public Page<Court> findByStadiumId(Long stadiumId, Pageable page);

    @Query("SELECT c.hourlyRentFee FROM Court c WHERE c.id = :id")
    public int findHourlyRentFeeById(Long id);

    @Query("SELECT c.name FROM Court c WHERE c.id = :id")
    public String findByName(Long id);
}
