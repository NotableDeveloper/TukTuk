package com.example.tuktuk.schedule.service;

import com.example.tuktuk.global.page.PageInfo;
import com.example.tuktuk.global.page.PageResponse;
import com.example.tuktuk.schedule.controller.dto.requestDto.ScheduleCreateReqDto;
import com.example.tuktuk.schedule.controller.dto.requestDto.ScheduleUpdateReqDto;
import com.example.tuktuk.schedule.controller.dto.responseDto.*;
import com.example.tuktuk.schedule.domain.*;
import com.example.tuktuk.schedule.repository.ScheduleRepository;
import com.example.tuktuk.global.Money;
import com.example.tuktuk.global.Province;
import com.example.tuktuk.security.SecurityContextHolderUtil;
import com.example.tuktuk.stadium.controller.dto.responseDto.stadium.StadiumSimpleReadResDto;
import com.example.tuktuk.stadium.domain.court.Court;
import com.example.tuktuk.stadium.domain.court.CourtId;
import com.example.tuktuk.stadium.domain.stadium.Stadium;
import com.example.tuktuk.stadium.repository.CourtRepository;
import com.example.tuktuk.stadium.repository.StadiumRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.example.tuktuk.schedule.domain.ReservationStatus.AVAILABLE;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {

  private final ScheduleRepository scheduleRepository;

  private final CourtRepository courtRepository;

  private final StadiumRepository stadiumRepository;

  @Transactional(readOnly = true)
  public ScheduleReadResponseDto findByScheduleId(long scheduleId) {
    Schedule schedule = scheduleRepository.findById(scheduleId)
        .orElseThrow(() -> new IllegalStateException("존재하지 않는 경기입니다."));
    int hourlyRentFee = courtRepository.findHourlyRentFeeById(schedule.getCourtId().getValue());
    return ScheduleReadResponseDto.from(schedule, hourlyRentFee);
  }

  @Transactional(readOnly = true)
  public PageResponse<ScheduleSimpleReadResDto> findByProvince(String province, LocalDate date, int pageNumber, int pageSize) {
    PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
    List<ScheduleSimpleReadResDto> response = new ArrayList<>();
    HashMap<Long, String> courtIdAndStadiumNames = new HashMap<>();

    stadiumRepository.findByProvince(Province.valueOf(province))
        .forEach(stadium -> stadium.getCourts()
            .forEach(court -> courtIdAndStadiumNames.put(
                court.getId(),
                stadium.getName()
            )));

    int totalPage = 0;
    int totalElements = 0;

    for (Long courtId : courtIdAndStadiumNames.keySet()) {
      String courtName = courtRepository.findByName(courtId);
      String stadiumName = courtIdAndStadiumNames.get(courtId);
      String stadiumWithCourtName = stadiumName + " " + courtName;

      Page<Schedule> schedulePage = scheduleRepository.findByCourtIdAndDate(courtId, date, pageRequest);
      totalPage += schedulePage.getTotalPages();
      totalElements += (int) schedulePage.getTotalElements();

      schedulePage.forEach(schedule ->
        response.add(ScheduleSimpleReadResDto.from(schedule, stadiumWithCourtName))
      );
    }

    return new PageResponse<>(response, new PageInfo(pageNumber, pageSize, totalElements, totalPage));
  }

  @Transactional
  public ScheduleCreateResDto saveSchedule(ScheduleCreateReqDto requestDto) {

    long courtId = requestDto.getCourtId();
    Court court = courtRepository.findById(courtId)
        .orElseThrow(() -> new IllegalStateException("잘못된 코트 참조입니다."));
    Stadium stadium = court.getStadium();
    Province province = stadium.getLocation().getProvince();

    Time time = Time.builder()
        .playDate(requestDto.getPlayDate())
        .startTime(requestDto.getStartTime())
        .endTime(requestDto.getEndTime())
        .build();

    int matchRegularFee = MatchRegularFeeManager.calculateRegularFee(province, time.getPlayDate());

    int hourlyRentFee = court.getHourlyRentFee();

    Schedule courtTimeSlot = Schedule.builder()
        .courtId(new CourtId(courtId))
        .time(time)
        .type(Type.valueOf(requestDto.getType()))
        .reservationStatus(AVAILABLE)
        .matchRegularFee(new Money(matchRegularFee))
        .isDeleted(false)
        .build();

    Schedule savedCourtTimeSlot = scheduleRepository.save(courtTimeSlot);
    return ScheduleCreateResDto.from(savedCourtTimeSlot, hourlyRentFee);
  }

  @Transactional
  public ScheduleUpdateResDto updateSchedule(long scheduleId, ScheduleUpdateReqDto requestDto) {
    Schedule schedule = scheduleRepository.findById(scheduleId)
        .orElseThrow(() -> new IllegalStateException("Schedule을 찾을 수 없습니다."));
    schedule.update(requestDto);
    Schedule updatedSchedule = scheduleRepository.save(schedule);
    int hourlyRentFee = courtRepository.findHourlyRentFeeById(schedule.getCourtId().getValue());
    return ScheduleUpdateResDto.from(updatedSchedule, hourlyRentFee);
  }

  @Transactional
  public ScheduleDeleteResDto deleteSchedule(long scheduleId) {
    Schedule schedule = scheduleRepository.findById(scheduleId)
        .orElseThrow(() -> new IllegalStateException("Schedule을 찾을 수 없습니다."));
    schedule.delete();
    Schedule saved = scheduleRepository.save(schedule);

    return ScheduleDeleteResDto.from(saved);
  }

  @Transactional
  public SchedulePerStadiumResDto findAllByOwnerIdAndStadiumId(long stadiumId,
      int pageNumber, int pageSize) {

    String ownerId = SecurityContextHolderUtil.getUserId();
    PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

    Stadium stadium = stadiumRepository.findByOwnerIdAndStadiumId(ownerId, stadiumId);

    List<ScheduleReadResponseDto> scheduleDtos = new ArrayList<>();

    int totalPage = 0;
    int totalElements = 0;

    for (Court c : stadium.getCourts()) {
      int hourlyRentFee = courtRepository.findHourlyRentFeeById(c.getId());
      Page<Schedule> schedulePage = scheduleRepository.findByCourtId(c.getId(), pageRequest);
      scheduleDtos.addAll(
          schedulePage
              .get()
              .map(schedule ->
                  ScheduleReadResponseDto.from(schedule, hourlyRentFee))
              .toList());
      totalPage += schedulePage.getTotalPages();
      totalElements += (int) schedulePage.getTotalElements();
    }

    return SchedulePerStadiumResDto
        .from(StadiumSimpleReadResDto.from(stadium),
            scheduleDtos,
            new PageInfo(pageNumber, pageSize, totalElements, totalPage)
        );
  }
}
