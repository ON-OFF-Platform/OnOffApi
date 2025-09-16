package kr.co.onmediagroup.onoffapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
