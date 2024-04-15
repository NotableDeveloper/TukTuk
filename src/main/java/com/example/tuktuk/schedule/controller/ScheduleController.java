package com.example.tuktuk.schedule.controller;

import com.example.tuktuk.global.page.PageResponse;
import com.example.tuktuk.schedule.controller.dto.requestDto.ReservationRequestDto;
import com.example.tuktuk.schedule.controller.dto.requestDto.ScheduleCreateReqDto;
import com.example.tuktuk.schedule.controller.dto.requestDto.ScheduleUpdateReqDto;
import com.example.tuktuk.schedule.controller.dto.responseDto.*;
import com.example.tuktuk.schedule.service.ScheduleService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
@Slf4j
public class ScheduleController {

  private final ScheduleService scheduleService;

  @GetMapping(value = "/{scheduleId}")
  public ScheduleReadResponseDto findByScheduleId(
      @PathVariable(name = "scheduleId") long scheduleId) {
    return scheduleService.findByScheduleId(scheduleId);
  }

  @GetMapping("/search")
  public PageResponse<ScheduleSimpleReadResDto> findByProvince(
      @RequestParam(name = "province") String province,
      @RequestParam(name = "date") LocalDate date,
      @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
      @RequestParam(name = "pageSize", defaultValue = "6") int pageSize
  ) {
    return scheduleService.findByProvince(province, date, pageNumber, pageSize);
  }

  @GetMapping("/my-schedules")
  public SchedulePerStadiumResDto findAllScheduleByOwner(
          @RequestParam(name = "stadiumId") long stadiumId) {
    return scheduleService.findAllByOwnerIdAndStadiumId(stadiumId);
  }

  @Secured({"USER", "FIELD_OWNER"})
  @PostMapping(value = "/match/{scheduleId}")
  public MatchEnrollResponseDto enrollMatch(@PathVariable long scheduleId){
    return scheduleService.registryMatch(scheduleId);
  }

  @Secured({"USER", "FIELD_OWNER"})
  @PostMapping(value = "/reservate")
  public ReservationResponseDto reservate(@RequestBody ReservationRequestDto requestDto){
    return scheduleService.reservate(requestDto);
  }

  @Secured("FIELD_OWNER")
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ScheduleCreateResDto createSchedule(@RequestBody ScheduleCreateReqDto requestDto) {
    return scheduleService.saveSchedule(requestDto);
  }

  @Secured("FIELD_OWNER")
  @PatchMapping(value = "/{scheduleId}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ScheduleUpdateResDto updateSchedule(@PathVariable(name = "scheduleId") long scheduleId,
      @RequestBody ScheduleUpdateReqDto requestDto) {
    return scheduleService.updateSchedule(scheduleId, requestDto);
  }

  @Secured("FIELD_OWNER")
  @DeleteMapping(value = "/{scheduleId}")
  public ScheduleDeleteResDto deleteSchedule(@PathVariable(name = "scheduleId") long scheduleId) {
    return scheduleService.deleteSchedule(scheduleId);
  }
}
