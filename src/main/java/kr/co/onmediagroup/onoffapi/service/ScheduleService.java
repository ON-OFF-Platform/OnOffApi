package kr.co.onmediagroup.onoffapi.service;

import kr.co.onmediagroup.onoffapi.exception.LoginException;
import kr.co.onmediagroup.onoffapi.exception.ScheduleException;
import kr.co.onmediagroup.onoffapi.model.dto.Schedules;
import kr.co.onmediagroup.onoffapi.model.entity.SchedulesEntity;
import kr.co.onmediagroup.onoffapi.model.entity.UserEntity;
import kr.co.onmediagroup.onoffapi.repository.SchedulesRepository;
import kr.co.onmediagroup.onoffapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {
  private final UserRepository userRepository;
  private final SchedulesRepository schedulesRepository;

  // 일정 생성
  public void createSchedule(
    String userId,
    Long scheduleCtgId,
    String content,
    LocalDateTime startTime,
    LocalDateTime endTime,
    Boolean isAllDay,
    String location
  ) {
    SchedulesEntity schedulesEntity = SchedulesEntity.builder()
      .userId(userId)
      .scheduleCtgId(scheduleCtgId)
      .content(content)
      .startTime(startTime)
      .endTime(endTime)
      .isAllDay(isAllDay)
      .location(location)
      .build();

    this.schedulesRepository.save(schedulesEntity);
  }

  // 일정 삭제(한개)
  public void deleteSchedule(String userId, String scheduleId) {
    // 일정 확인
    SchedulesEntity schedulesEntity = this.schedulesRepository.findById(scheduleId)
      .orElseThrow(ScheduleException.NoSchedule::new);

    // 소유자 체크
    if(!schedulesEntity.getUserId().equals(userId)) {
      throw new ScheduleException.AccessDenied();
    }

    this.schedulesRepository.delete(schedulesEntity);
  }

  // 해당 날짜의 모든 일정 삭제
  public void deleteAllSchedules(String userId, String date) {
    // 유저 확인
    UserEntity userEntity = this.userRepository.findById(userId)
      .orElseThrow(LoginException.NoUser::new);

    // 날짜 형식으로 변형, "yyyy-MM-dd"
    LocalDate localDate = LocalDate.parse(date);
    LocalDateTime startOfDay = localDate.atStartOfDay();
    LocalDateTime endOfDay = localDate.atTime(23, 59, 59);

    List<SchedulesEntity> schedulesEntityList = this.schedulesRepository
      .findByUserIdAndStartTimeBetween(userId, startOfDay, endOfDay);

    if(schedulesEntityList.isEmpty()) {
      throw new ScheduleException.NoSchedule();
    }

    this.schedulesRepository.deleteAll(schedulesEntityList);
  }

  // 일정 조회
  public List<Schedules.ScheduleAndCtgAndColorDTO> findSchedule(String userId, String year, String month) {

    // 일정 조회
    List<Schedules.ScheduleAndCtgAndColorDTO> schedulesDTOList = this.schedulesRepository.findByUserIdAndYearAndMonth(
      userId,
      year,
      month
    );

    return schedulesDTOList;
  }

  public void updateSchedule(
    String userId,
    String scheduleId,
    Long scheduleCtgId,
    String content,
    LocalDateTime startTime,
    LocalDateTime endTime,
    Boolean isAllDay,
    String location
  ) {
    // 유효성 검사
    SchedulesEntity schedulesEntity = schedulesRepository.findByScheduleIdAndUserId(scheduleId, userId)
      .orElseThrow(ScheduleException.NoSchedule::new);

    if (!Objects.equals(schedulesEntity.getScheduleCtgId(), scheduleCtgId)) {
      schedulesEntity.setScheduleCtgId(scheduleCtgId);
    }

    if (content != null && !content.isBlank() && !schedulesEntity.getContent().equals(content)) {
      schedulesEntity.setContent(content);
    }

    if (startTime != null && !schedulesEntity.getStartTime().equals(startTime)) {
      schedulesEntity.setStartTime(startTime);
    }

    if (endTime != null && !schedulesEntity.getEndTime().equals(endTime)) {
      schedulesEntity.setEndTime(endTime);
    }

    if (isAllDay != null && !schedulesEntity.getIsAllDay().equals(isAllDay)) {
      schedulesEntity.setIsAllDay(isAllDay);
    }

    if (location != null && !location.isBlank() && !schedulesEntity.getLocation().equals(location)) {
      schedulesEntity.setLocation(location);
    }
    try {
      schedulesRepository.saveAndFlush(schedulesEntity);
    } catch (Exception e) {
      log.error("Commit failed", e);
      throw e;
    }
  }
}
