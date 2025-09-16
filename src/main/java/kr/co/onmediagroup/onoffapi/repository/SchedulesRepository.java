package kr.co.onmediagroup.onoffapi.repository;

import kr.co.onmediagroup.onoffapi.model.entity.SchedulesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SchedulesRepository extends JpaRepository<SchedulesEntity, String> {

  @Query("""
    SELECT schedules FROM SchedulesEntity schedules
    WHERE schedules.userId = :userId
      AND (
            (FUNCTION('YEAR', schedules.startTime) = :year AND FUNCTION('MONTH', schedules.startTime) = :month)
         OR (FUNCTION('YEAR', schedules.endTime) = :year AND FUNCTION('MONTH', schedules.endTime) = :month)
      )
  """)
  List<SchedulesEntity> findByUserIdAndYearAndMonth(
    @Param("userId") String userId,
    @Param("year") String year,
    @Param("month") String month
  );

  List<SchedulesEntity> findByUserIdAndStartTimeBetween(String userId, LocalDateTime startOfDay, LocalDateTime endOfDay);

  List<SchedulesEntity> findByUserIdAndScheduleCtgId(String userId, Long scheduleCtgId);
}
