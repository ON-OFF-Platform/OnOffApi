package kr.co.onmediagroup.onoffapi.controller;

import jakarta.validation.Valid;
import kr.co.onmediagroup.onoffapi.model.dto.Schedules;
import kr.co.onmediagroup.onoffapi.model.dto.User;

import kr.co.onmediagroup.onoffapi.model.vo.SchedulesVO;
import kr.co.onmediagroup.onoffapi.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {
  private final ScheduleService scheduleService;

  /**
   * 일정 생성
   * @param userPrincipal
   * @param schedulesReqVO
   */
  @PostMapping("/create")
  @ResponseStatus(value = HttpStatus.CREATED)
  public void createSchedule(
    @AuthenticationPrincipal User.MinimumUserPrincipal userPrincipal,
    @RequestBody @Valid SchedulesVO.ScheduleReqVO schedulesReqVO
  ) {
    scheduleService.createSchedule(
      userPrincipal.getUserId(),
      schedulesReqVO.scheduleCtgId(),
      schedulesReqVO.content(),
      schedulesReqVO.startTime(),
      schedulesReqVO.endTime(),
      schedulesReqVO.isAllDay(),
      schedulesReqVO.location()
    );
  }

  @PostMapping("/update/{date}")
  @ResponseStatus(value = HttpStatus.OK)
  public void updateSchedule(
    @AuthenticationPrincipal User.MinimumUserPrincipal userPrincipal,
    @PathVariable String date,
    @RequestBody @Valid SchedulesVO.ScheduleUpdateVO scheduleUpdateVO
  ) {
    scheduleService.updateSchedule(
      userPrincipal.getUserId(),
      date,
      scheduleUpdateVO.scheduleId(),
      scheduleUpdateVO.scheduleCtgId(),
      scheduleUpdateVO.content(),
      scheduleUpdateVO.startTime(),
      scheduleUpdateVO.endTime(),
      scheduleUpdateVO.isAllDay(),
      scheduleUpdateVO.location()
    );
  }

  /**
   * 일정 삭제 - 한개
   * @param userPrincipal
   * @param scheduleDeleteVO
   */
  @PostMapping("/delete")
  @ResponseStatus(value = HttpStatus.OK)
  public void deleteSchedule(
    @AuthenticationPrincipal User.MinimumUserPrincipal userPrincipal,
    @RequestBody @Valid SchedulesVO.ScheduleDeleteVO scheduleDeleteVO
  ) {
    scheduleService.deleteSchedule(
      userPrincipal.getUserId(),
      scheduleDeleteVO.scheduleId()
    );
  }

  /**
   * 해당 날짜의 모든 일정 삭제
   * @param userPrincipal
   * @param date
   */
  @PostMapping("/delete/{date}")
  @ResponseStatus(value = HttpStatus.OK)
  public void deleteAllSchedules(
    @AuthenticationPrincipal User.MinimumUserPrincipal userPrincipal,
    @PathVariable String date
  ) {
    scheduleService.deleteAllSchedules(
      userPrincipal.getUserId(),
      date
    );
  }

  /**
   * 일정 조회
   * @param userPrincipal
   * @param year 조회할 연도 2025
   * @param month 조회할 월 9, 09
   * @return 해당 연도-달의 일정 리스트
   */
  @GetMapping("/calender/{year}/{month}")
  @ResponseStatus(value = HttpStatus.OK)
  public List<Schedules.ScheduleAndCtgAndColorDTO> findSchedule(
    @AuthenticationPrincipal User.MinimumUserPrincipal userPrincipal,
    @PathVariable String year,
    @PathVariable String month
    ) {
      List<Schedules.ScheduleAndCtgAndColorDTO> schedulesDTOS = scheduleService.findSchedule(
        userPrincipal.getUserId(),
        year,
        month
      );

      return schedulesDTOS;
  }
}
