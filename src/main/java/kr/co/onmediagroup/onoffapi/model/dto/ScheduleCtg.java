package kr.co.onmediagroup.onoffapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ScheduleCtg {

  @Builder
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ScheduleCtgDTO {
    private Long scheduleCtgId;
    private String userId;
    private String name;
    private Integer sortOrder;
    private Integer colorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
  }
}
