package com.example.tuktuk.stadium.controller;

import com.example.tuktuk.global.page.PageResponse;
import com.example.tuktuk.stadium.controller.dto.requestDto.stadium.StadiumCreateRequestDto;
import com.example.tuktuk.stadium.controller.dto.requestDto.stadium.StadiumUpdateRequestDto;
import com.example.tuktuk.stadium.controller.dto.responseDto.stadium.StadiumWithCourtsResDto;
import com.example.tuktuk.stadium.controller.dto.responseDto.stadium.StadiumCreateResponseDto;
import com.example.tuktuk.stadium.controller.dto.responseDto.stadium.StadiumDeleteResponseDto;
import com.example.tuktuk.stadium.controller.dto.responseDto.stadium.StadiumReadResponseDto;
import com.example.tuktuk.stadium.controller.dto.responseDto.stadium.StadiumSimpleReadResDto;
import com.example.tuktuk.stadium.controller.dto.responseDto.stadium.StadiumUpdateResponseDto;
import com.example.tuktuk.stadium.service.CourtService;
import com.example.tuktuk.stadium.service.StadiumService;
import com.example.tuktuk.security.SecurityContextHolderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stadiums")
@RequiredArgsConstructor
@Slf4j
public class StadiumController {

  private final StadiumService stadiumService;

  private final CourtService courtService;

  @GetMapping("/{stadiumId}")
  public StadiumReadResponseDto getStadiumById(@PathVariable(name = "stadiumId") long stadiumId) {
    return stadiumService.findByStadiumId(stadiumId);
  }

  @GetMapping("/{stadiumId}/courts")
  public StadiumWithCourtsResDto getStadiumWithCourts(
      @PathVariable(name = "stadiumId") long stadiumId,
      @RequestParam(name = "pageNumber", defaultValue = "0", required = false) int pageNumber,
      @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
    return courtService.findByStadiumId(stadiumId, pageNumber, pageSize);
  }

  @GetMapping("/search")
  public PageResponse<StadiumSimpleReadResDto> getByKeyword(
      @RequestParam(name = "keyword") String keyword,
      @RequestParam(name = "pageNumber", defaultValue = "0", required = false) int pageNumber,
      @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize) {
    return stadiumService.findByKeyword(keyword, pageNumber, pageSize);
  }

  @GetMapping("/my-stadiums")
  public PageResponse<StadiumReadResponseDto> getMyStadiums(
      @RequestParam(name = "pageNumber", defaultValue = "0", required = false) int pageNumber,
      @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize) {
    return stadiumService.findByOwnerId(pageNumber, pageSize);
  }

  @Secured("FIELD_OWNER")
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public StadiumCreateResponseDto createStadium(@RequestBody StadiumCreateRequestDto requestDto) {
    return stadiumService.saveStadium(requestDto);
  }

  @Secured("FIELD_OWNER")
  @PatchMapping(value = "/{stadiumId}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public StadiumUpdateResponseDto updateStadium(
      @PathVariable(name = "stadiumId") long stadiumId,
      @RequestBody StadiumUpdateRequestDto requestDto) {

    return stadiumService.updateStadium(stadiumId, requestDto);
  }

  @Secured("FIELD_OWNER")
  @DeleteMapping("/{stadiumId}")
  public StadiumDeleteResponseDto deleteStadium(@PathVariable(name = "stadiumId") long stadiumId) {
    String ownerId = SecurityContextHolderUtil.getUserId();
    return stadiumService.deleteStadium(ownerId, stadiumId);
  }
}
