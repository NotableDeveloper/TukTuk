package com.example.tuktuk.stadium.repository;

import com.example.tuktuk.stadium.domain.court.CourtImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourtImageRepository extends JpaRepository<CourtImage, Long> {
    public CourtImage findByImagePath(String imagePath);
}
