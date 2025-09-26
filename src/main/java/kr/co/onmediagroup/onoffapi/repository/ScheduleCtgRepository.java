package kr.co.onmediagroup.onoffapi.repository;

import kr.co.onmediagroup.onoffapi.model.dto.ScheduleCtg;
import kr.co.onmediagroup.onoffapi.model.entity.ScheduleCtgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleCtgRepository extends JpaRepository<ScheduleCtgEntity, Long> {

  List<ScheduleCtgEntity> findByUserIdAndSortOrderGreaterThanEqual(String userId, Integer sortOrder);

  @Query("SELECT COALESCE(MAX(c.sortOrder), 0) FROM ScheduleCtgEntity c WHERE c.userId = :userId")
  Integer findMaxSortOrderByUserId(@Param("userId") String userId);

  @Query("""
    SELECT new kr.co.onmediagroup.onoffapi.model.dto.ScheduleCtg$ScheduleCtgResDTO(
      s.scheduleCtgId,
      s.userId,
      s.name,
      s.sortOrder,
      s.colorId,
      c.colorName
    )
    FROM ScheduleCtgEntity s 
    JOIN s.color c
    WHERE s.userId = :userId 
    ORDER BY s.sortOrder asc
  """)
  List<ScheduleCtg.ScheduleCtgResDTO> findByUserId(@Param("userId") String userId);
}
