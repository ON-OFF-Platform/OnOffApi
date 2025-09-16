package kr.co.onmediagroup.onoffapi.service;

import kr.co.onmediagroup.onoffapi.exception.LoginException;
import kr.co.onmediagroup.onoffapi.exception.ScheduleException;
import kr.co.onmediagroup.onoffapi.model.ModelConverter;
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
import java.util.stream.Collectors;

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
  public List<Schedules.SchedulesDTO> findSchedule(String userId, String year, String month) {

    // 일정 조회
    List<SchedulesEntity> schedulesEntities = this.schedulesRepository.findByUserIdAndYearAndMonth(
      userId,
      year,
      month
    );

    List<Schedules.SchedulesDTO> schedulesDTOList = schedulesEntities.stream().map(
      entity -> ModelConverter.MODEL_MAPPER.map(entity, Schedules.SchedulesDTO.class)
    ).collect(Collectors.toList());

    return schedulesDTOList;
  }

}
