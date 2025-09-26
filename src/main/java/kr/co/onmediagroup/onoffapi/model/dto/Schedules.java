package kr.co.onmediagroup.onoffapi.model.dto;

import lombok.*;

import java.time.LocalDateTime;

public class Schedules {

  @Builder
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class SchedulesDTO {
    private String scheduleId;
    private String userId;
    private Long scheduleCtgId;
    private String content;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isAllDay;
    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
  }

  @Builder
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class SchedulesReqDTO {
    private Long scheduleCtgId;
    private String content;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isAllDay;
    private String location;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ScheduleAndCtgAndColorDTO {
    private String scheduleId;
    private Long scheduleCtgId;
    private String userId;
    private String content;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isAllDay;
    private String location;
    private Integer colorId;
    private String name;
    private Integer sortOrder;
    private String colorName;
  }
}
