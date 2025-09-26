package kr.co.onmediagroup.onoffapi.model.dto;

import lombok.*;

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

  @Builder
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ScheduleCtgResDTO {
    private Long scheduleCtgId;
    private String userId;
    private String name;
    private Integer sortOrder;
    private Integer colorId;
    private String colorName;
  }
}
