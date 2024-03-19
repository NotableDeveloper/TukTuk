package com.example.tuktuk.stadium.service;

import com.example.tuktuk.stadium.controller.dto.requestDto.court.CourtCreateRequestDto;
import com.example.tuktuk.stadium.controller.dto.requestDto.court.CourtImageDeleteRequestDto;
import com.example.tuktuk.stadium.controller.dto.requestDto.court.CourtImageUpdateRequestDto;
import com.example.tuktuk.stadium.controller.dto.requestDto.court.CourtUpdateRequestDto;
import com.example.tuktuk.stadium.controller.dto.responseDto.stadium.StadiumWithCourtsResDto;
import com.example.tuktuk.stadium.controller.dto.responseDto.court.*;
import com.example.tuktuk.stadium.controller.dto.responseDto.stadium.StadiumReadResponseDto;
import com.example.tuktuk.stadium.domain.court.Court;
import com.example.tuktuk.stadium.domain.court.CourtImage;
import com.example.tuktuk.stadium.domain.court.CourtType;
import com.example.tuktuk.stadium.domain.stadium.Stadium;
import com.example.tuktuk.stadium.repository.CourtImageRepository;
import com.example.tuktuk.stadium.repository.CourtRepository;
import com.example.tuktuk.stadium.repository.StadiumRepository;
import com.example.tuktuk.stadium.util.image.ObjectStorageFunction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourtService {
    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private ObjectStorageFunction storageManager;

    @Autowired
    private StadiumRepository stadiumRepository;

    @Autowired
    private CourtImageRepository courtImageRepository;

    @Transactional(readOnly = true)
    public CourtReadResponseDto findByCourtId(Long courtId) {
        return CourtReadResponseDto.from(courtRepository.findById(courtId).get());
    }

    @Transactional(readOnly = true)
    public StadiumWithCourtsResDto findByStadiumId(Long stadiumId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        Stadium stadium = stadiumRepository.findById(stadiumId).get();

        return StadiumWithCourtsResDto.from(StadiumReadResponseDto.from(stadium),
                courtRepository.findByStadiumId(stadiumId, pageRequest));
    }

    @Transactional
    public CourtCreateResponseDto saveCourt(CourtCreateRequestDto request, List<MultipartFile> images) {
        Court savedCourt;

        Stadium parentStadium = stadiumRepository.findById(request.getStadiumId())
                .orElseThrow(() -> new RuntimeException("찾을 수 없는 경기장입니다."));

        if (images == null || images.isEmpty()) {
            Court court = Court.builder()
                    .stadium(parentStadium)
                    .name(request.getName())
                    .courtType(CourtType.valueOf(request.getCourtType()))
                    .images(new ArrayList<>())
                    .hourlyRentFee(request.getHourlyRentFee()).build();

            savedCourt = courtRepository.save(court);
        } else {
            if (images.size() > 3) throw new RuntimeException("사진은 최대 3장까지 등록할 수 있습니다.");

            Court court = Court.builder()
                    .stadium(parentStadium)
                    .name(request.getName())
                    .courtType(CourtType.valueOf(request.getCourtType()))
                    .images(new ArrayList<>())
                    .hourlyRentFee(request.getHourlyRentFee())
                    .build();

            savedCourt = courtRepository.save(court);

            List<CourtImage> savedImages = insertCourtImages(savedCourt, images);
            savedCourt.setImages(savedImages);
        }

        return CourtCreateResponseDto.from(savedCourt);
    }

    @Transactional
    public CourtUpdateResponseDto updateCourtInfo(Long courtId,
                                                  CourtUpdateRequestDto request) {

        Court oldCourt = courtRepository.findById(courtId)
                .orElseThrow(() -> new RuntimeException("찾을 수 없는 경기장입니다."));

        Court court = Court.builder()
                .id(oldCourt.getId())
                .name(request.getName())
                .courtType(CourtType.valueOf(request.getCourtType()))
                .hourlyRentFee(request.getHourlyRentFee())
                .stadium(oldCourt.getStadium())
                .images(oldCourt.getImages())
                .build();

        Court updatedCourt = courtRepository.save(court);

        return CourtUpdateResponseDto.from(updatedCourt);
    }

    @Transactional
    public CourtImageUpdateResponseDto updateCourtImages(Long courtId,
                                                         CourtImageUpdateRequestDto request,
                                                         List<MultipartFile> images) {

        Court oldCourt = courtRepository.findById(courtId)
                .orElseThrow(() -> new RuntimeException("찾을 수 없는 코트입니다."));

        List<CourtImage> updateImages = updateCourtImages(courtId, request.getImagePaths(), images);

        oldCourt.setImages(updateImages);

        Court updatedCourt = courtRepository.save(oldCourt);

        return CourtImageUpdateResponseDto.from(updatedCourt);
    }

    /*
        deleteImagePaths : 수정할 이미지의 S3 URL 주소
        images : 새롭게 등록할 이미지

        S3에 저장되어 있는 이미지를 덮어쓰는 것이 아니라, deleteImagePaths에 있는 이미지들을
        먼저 지우고 난 이후에 images에 있는 이미지들을 S3에 저장한다.

        경우에 따라 이미지만 추가로 등록하는 것도 가능하다.
    */
    @Transactional
    private List<CourtImage> updateCourtImages(Long courtId,
                                               List<String> deleteImagePaths,
                                               List<MultipartFile> images) {

        deleteCourtImage(deleteImagePaths);
        return insertCourtImages(courtRepository.findById(courtId).get(), images);
    }

    @Transactional
    public CourtDeleteResponseDto deleteCourt(long courtId) {

        Court court = courtRepository.findById(courtId)
                .orElseThrow(() -> new RuntimeException("찾을 수 없는 코트입니다."));

        List<String> deleteImagePaths = court.getImages()
                .stream()
                .map(CourtImage::getImagePath)
                .toList();

        deleteCourtImage(deleteImagePaths);
        courtRepository.delete(court);

        return CourtDeleteResponseDto.from(court);
    }

    @Transactional
    private List<CourtImage> insertCourtImages(Court court, List<MultipartFile> images) {
        List<CourtImage> courtImages = images.stream().map(image -> {
            String savedObjectName = storageManager.putObject(image);
            String objectPath = storageManager.getObject(savedObjectName);

            return CourtImage
                    .builder()
                    .court(court)
                    .imagePath(objectPath)
                    .build();
        }).toList();

        List<CourtImage> savedCourtImages = courtImageRepository.saveAll(courtImages);

        return savedCourtImages;
    }

    @Transactional
    private void deleteCourtImage(List<String> deleteImagePaths) {
        deleteImagePaths.forEach(deleteImagePath -> {
                    CourtImage oldCourtImage = courtImageRepository.findByImagePath(deleteImagePath);
                    courtImageRepository.delete(oldCourtImage);
                    storageManager.deleteObject(deleteImagePath);
                }
        );
    }

    public CourtImageDeleteResponseDto deleteCourtImage(Long courtId,
                                                        CourtImageDeleteRequestDto request) {
        deleteCourtImage(request.getDeleteImagePaths());
        Court court = courtRepository.findById(courtId).orElseThrow(() -> new RuntimeException("찾을 수 없는 코트입니다."));
        return CourtImageDeleteResponseDto.from(court);
    }
}
