package com.example.tuktuk.schedule.repository;

import com.example.tuktuk.schedule.domain.Schedule;

import com.example.tuktuk.schedule.domain.Type;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s WHERE s.id = :id AND s.isDeleted = false")
    public Optional<Schedule> findById(Long id);

    @Query("SELECT s FROM Schedule s WHERE s.id = :id AND s.isDeleted = false " +
            "AND s.reservationStatus = 'AVAILABLE' " +
            "AND s.type = :type")
    public Optional<Schedule> findByIdAndType(Long id, Type type);

    @Query("SELECT s FROM Schedule s WHERE s.courtId.id = :courtId")
    public List<Schedule> findByCourtId(Long courtId);

    @Query("SELECT s FROM Schedule s " +
            "WHERE s.courtId.id = :courtId " +
            "AND s.time.playDate = :date " +
            "AND s.isDeleted = false"
    )
    public Page<Schedule> findByCourtIdAndDate(Long courtId, LocalDate date, Pageable page);
}
