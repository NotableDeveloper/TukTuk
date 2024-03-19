package com.example.tuktuk.stadium.service;

import com.example.tuktuk.global.page.PageInfo;
import com.example.tuktuk.global.page.PageResponse;
import com.example.tuktuk.security.SecurityContextHolderUtil;
import com.example.tuktuk.stadium.controller.dto.requestDto.stadium.StadiumCreateRequestDto;
import com.example.tuktuk.stadium.controller.dto.requestDto.stadium.StadiumUpdateRequestDto;
import com.example.tuktuk.stadium.controller.dto.responseDto.stadium.StadiumCreateResponseDto;
import com.example.tuktuk.stadium.controller.dto.responseDto.stadium.StadiumDeleteResponseDto;
import com.example.tuktuk.stadium.controller.dto.responseDto.stadium.StadiumReadResponseDto;
import com.example.tuktuk.stadium.controller.dto.responseDto.stadium.StadiumSimpleReadResDto;
import com.example.tuktuk.stadium.controller.dto.responseDto.stadium.StadiumUpdateResponseDto;
import com.example.tuktuk.stadium.domain.Location;
import com.example.tuktuk.stadium.domain.stadium.Stadium;
import com.example.tuktuk.stadium.repository.StadiumRepository;
import com.example.tuktuk.users.domain.UserId;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class StadiumService {

  @Autowired
  private final StadiumRepository stadiumRepository;

  @Transactional(readOnly = true)
  public StadiumReadResponseDto findByStadiumId(final long stadiumId) {
    Stadium stadium = stadiumRepository.findById(stadiumId)
        .orElseThrow(() -> new IllegalStateException("잘못된 접근입니다."));
    return StadiumReadResponseDto.from(stadium);
  }

  @Transactional(readOnly = true)
  public PageResponse<StadiumReadResponseDto> findByOwnerId(int pageNumber, int pageSize) {

    PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
    String ownerId = SecurityContextHolderUtil.getUserId();
    Page<Stadium> stadiumPage = stadiumRepository.findByOwnerId(ownerId, pageRequest);

    return new PageResponse<>(stadiumPage
        .map(StadiumReadResponseDto::from).toList(),
        PageInfo.from(stadiumPage));
  }

  @Transactional(readOnly = true)
  public List<StadiumReadResponseDto> findAll() {
    List<Stadium> stadiums = stadiumRepository.findAll();
    return stadiums.stream()
        .map(StadiumReadResponseDto::from)
        .collect(Collectors.toList());
  }

  @Transactional
  public StadiumCreateResponseDto saveStadium(StadiumCreateRequestDto request) {
    String ownerId = SecurityContextHolderUtil.getUserId();

    Stadium stadium = Stadium.builder()
        .name(request.getName())
        .ownerId(new UserId(ownerId))
        .location(Location.of(request.getLocationReqDto()))
        .specificInfo(request.getSpecificInfo())
        .build();

    Stadium savedStadium = stadiumRepository.save(stadium);
    return StadiumCreateResponseDto.from(savedStadium);
  }

  @Transactional
  public StadiumUpdateResponseDto updateStadium(long stadiumId,
      StadiumUpdateRequestDto request) {

    String ownerId = SecurityContextHolderUtil.getUserId();

    Stadium stadium = stadiumRepository.findById(stadiumId)
        .orElseThrow(() -> new IllegalStateException("잘못된 접근입니다."));

    if (!stadium.getOwnerId().getUserId().equals(ownerId)) {
      throw new IllegalStateException("수정할 권한이 없습니다.");
    }

    stadium.update(request);
    Stadium updatedStadium = stadiumRepository.save(stadium);
    return StadiumUpdateResponseDto.from(updatedStadium);
  }

  @Transactional
  public StadiumDeleteResponseDto deleteStadium(final String ownerId, final long stadiumId) {
    Stadium stadium = stadiumRepository.findById(stadiumId)
        .orElseThrow(() -> new IllegalStateException("잘못된 접근입니다."));
    if (!stadium.getOwnerId().getUserId().equals(ownerId)) {
      throw new IllegalStateException("삭제할 권한이 없습니다.");
    }
    stadiumRepository.delete(stadium);

    return StadiumDeleteResponseDto.from(stadium);
  }

  public PageResponse<StadiumSimpleReadResDto> findByKeyword(String keyword, int pageNumber,
      int pageSize) {
    PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

    Page<Stadium> stadiumPage = stadiumRepository.findByKeyword(keyword, pageRequest);

    List<StadiumSimpleReadResDto> stadiums = stadiumPage.get()
        .map(StadiumSimpleReadResDto::from)
        .toList();

    return new PageResponse<StadiumSimpleReadResDto>(stadiums, PageInfo.from(stadiumPage));
  }
}
