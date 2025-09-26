package kr.co.onmediagroup.onoffapi.service;

import kr.co.onmediagroup.onoffapi.exception.ScheduleException;
import kr.co.onmediagroup.onoffapi.model.ModelConverter;
import kr.co.onmediagroup.onoffapi.model.dto.Color;
import kr.co.onmediagroup.onoffapi.model.dto.ScheduleCtg;
import kr.co.onmediagroup.onoffapi.model.entity.ColorEntity;
import kr.co.onmediagroup.onoffapi.model.entity.ScheduleCtgEntity;
import kr.co.onmediagroup.onoffapi.model.entity.SchedulesEntity;
import kr.co.onmediagroup.onoffapi.repository.ColorRepository;
import kr.co.onmediagroup.onoffapi.repository.ScheduleCtgRepository;
import kr.co.onmediagroup.onoffapi.repository.SchedulesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleCtgService {
  private final ScheduleCtgRepository scheduleCtgRepository;
  private final SchedulesRepository schedulesRepository;
  private final ColorRepository colorRepository;

  // 카테고리 생성
  public void createCtg(
    String userId,
    String name,
    Integer sortOrder,
    Integer colorId
  ) {
    // 정렬 순서
    Integer maxSortOrder = this.scheduleCtgRepository.findMaxSortOrderByUserId(userId);

    if (sortOrder == null || sortOrder < 1 || (maxSortOrder != null && sortOrder > maxSortOrder + 1)) {
      sortOrder = maxSortOrder == null ? 1 : maxSortOrder + 1;
    }

    // 기존 카테고리 순서 변경
    List<ScheduleCtgEntity> scheduleCtgEntityList = this.scheduleCtgRepository.findByUserIdAndSortOrderGreaterThanEqual(userId, sortOrder);

    for (ScheduleCtgEntity ctg : scheduleCtgEntityList) {
      ctg.setSortOrder(ctg.getSortOrder() + 1);
    }
    this.scheduleCtgRepository.saveAll(scheduleCtgEntityList);

    // 새로운 카테고리 저장
    ScheduleCtgEntity scheduleCtgEntity = ScheduleCtgEntity.builder()
      .userId(userId)
      .name(name)
      .sortOrder(sortOrder)
      .colorId(colorId)
      .build();

    this.scheduleCtgRepository.save(scheduleCtgEntity);
  }

  // 카테고리 조회
  public List<ScheduleCtg.ScheduleCtgResDTO> findAll(String userId) {
    List<ScheduleCtg.ScheduleCtgResDTO> scheduleCtgEntityList = this.scheduleCtgRepository.findByUserId(userId);

    if (scheduleCtgEntityList.isEmpty()) {
      throw new ScheduleException.NoScheduleCtg();
    }

    return scheduleCtgEntityList;
  }

  // 카테고리 업데이트
  public void updateCtg(
    String userId,
    Long scheduleCtgId,
    String name,
    Integer sortOrder,
    Integer colorId
  ) {
    // 카테고리 확인
    ScheduleCtgEntity scheduleCtgEntity = this.scheduleCtgRepository.findById(scheduleCtgId)
      .orElseThrow(ScheduleException.NoScheduleCtg::new);

    // 소유자 확인
    if (!scheduleCtgEntity.getUserId().equals(userId)) {
      throw new ScheduleException.AccessDenied();
    }

    // 값 업데이트
    if (name != null && !name.isBlank() && !scheduleCtgEntity.getName().equals(name) ) {
      scheduleCtgEntity.setName(name);
    }

    if (sortOrder != null && !scheduleCtgEntity.getSortOrder().equals(sortOrder)) {
      // 해당 사용자 최대 sortOrder 조회
      Integer maxSortOrder = this.scheduleCtgRepository.findMaxSortOrderByUserId(userId);
      if (sortOrder < 1) sortOrder = 1;
      if (sortOrder > maxSortOrder) sortOrder = maxSortOrder;

      // 다른 카테고리 순서 조정
      List<ScheduleCtgEntity> affectedCtgList =
        this.scheduleCtgRepository.findByUserIdAndSortOrderGreaterThanEqual(userId, sortOrder);
      for (ScheduleCtgEntity ctg : affectedCtgList) {
        if (!ctg.getScheduleCtgId().equals(scheduleCtgId)) {
          ctg.setSortOrder(ctg.getSortOrder() + 1);
        }
      }
      this.scheduleCtgRepository.saveAll(affectedCtgList);

      scheduleCtgEntity.setSortOrder(sortOrder);
    }

    if (colorId != null && !scheduleCtgEntity.getColorId().equals(colorId)) {
      scheduleCtgEntity.setColorId(colorId);
    }

    this.scheduleCtgRepository.save(scheduleCtgEntity);
  }

  // 카테고리 삭제
  public void deleteCtg(
    String userId,
    Long scheduleCtgId
  ) {
    // 카테고리 확인
    ScheduleCtgEntity scheduleCtgEntity = this.scheduleCtgRepository.findById(scheduleCtgId)
      .orElseThrow(ScheduleException.NoScheduleCtg::new);

    // 해당 카테고리로 지정되었던 일정들 기본 카테고리(0)로 변경
    List<SchedulesEntity> schedulesEntityList = this.schedulesRepository.findByUserIdAndScheduleCtgId(
      userId,
      scheduleCtgId
    );

    for (SchedulesEntity entity : schedulesEntityList) {
      entity.setScheduleCtgId(0L);
    }
    this.schedulesRepository.saveAll(schedulesEntityList);

    this.scheduleCtgRepository.delete(scheduleCtgEntity);
  }

  public List<Color.ColorDTO> findColor() {
    List<ColorEntity> colorEntityList = this.colorRepository.findAll();

    List<Color.ColorDTO> colorDTOList = colorEntityList.stream().map(
      entity -> ModelConverter.MODEL_MAPPER.map(entity, Color.ColorDTO.class)).collect(Collectors.toList());

    return colorDTOList;
  }
}
