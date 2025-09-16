package kr.co.onmediagroup.onoffapi.repository;

import kr.co.onmediagroup.onoffapi.model.entity.ScheduleCtgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleCtgRepository extends JpaRepository<ScheduleCtgEntity, Long> {

  List<ScheduleCtgEntity> findByUserIdAndSortOrderGreaterThanEqual(String userId, Integer sortOrder);

  @Query("SELECT COALESCE(MAX(c.sortOrder), 0) FROM ScheduleCtgEntity c WHERE c.userId = :userId")
  Integer findMaxSortOrderByUserId(@Param("userId") String userId);

  List<ScheduleCtgEntity> findByUserId(String userId);
}
