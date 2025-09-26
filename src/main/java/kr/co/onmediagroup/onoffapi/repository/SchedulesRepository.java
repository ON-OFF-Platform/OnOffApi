package kr.co.onmediagroup.onoffapi.repository;

import kr.co.onmediagroup.onoffapi.model.dto.Schedules;
import kr.co.onmediagroup.onoffapi.model.entity.SchedulesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SchedulesRepository extends JpaRepository<SchedulesEntity, String> {

  @Query("""
    SELECT new kr.co.onmediagroup.onoffapi.model.dto.Schedules$ScheduleAndCtgAndColorDTO(
        s.scheduleId,
        s.scheduleCtgId,
        s.userId,
        s.content,
        s.startTime,
        s.endTime,
        s.isAllDay,
        s.location,
        sc.colorId,
        sc.name,
        sc.sortOrder,
        c.colorName
    )
    FROM SchedulesEntity s
    JOIN s.scheduleCtg sc
    JOIN sc.color c
    WHERE s.userId = :userId
      AND (
            (FUNCTION('YEAR', s.startTime) = :year AND FUNCTION('MONTH', s.startTime) = :month)
         OR (FUNCTION('YEAR', s.endTime) = :year AND FUNCTION('MONTH', s.endTime) = :month)
      )
  """)
  List<Schedules.ScheduleAndCtgAndColorDTO> findByUserIdAndYearAndMonth(
    @Param("userId") String userId,
    @Param("year") String year,
    @Param("month") String month
  );

  List<SchedulesEntity> findByUserIdAndStartTimeBetween(String userId, LocalDateTime startOfDay, LocalDateTime endOfDay);

  List<SchedulesEntity> findByUserIdAndScheduleCtgId(String userId, Long scheduleCtgId);

  Optional<SchedulesEntity> findByScheduleIdAndUserId(String scheduleId, String userId);
}
