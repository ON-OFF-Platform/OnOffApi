package kr.co.onmediagroup.onoffapi.model.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class SchedulesVO {

  public record ScheduleReqVO(
    @NotNull Long scheduleCtgId,
    @NotBlank @Size(max = 255) String content,
    @NotNull LocalDateTime startTime,
    @NotNull LocalDateTime endTime,
    @NotNull Boolean isAllDay,
    @Size(max = 200) String location
  ) {}

  public record ScheduleUpdateVO(
    @NotNull String scheduleId,
    @NotNull Long scheduleCtgId,
    @NotBlank @Size(max = 255) String content,
    @NotNull LocalDateTime startTime,
    @NotNull LocalDateTime endTime,
    @NotNull Boolean isAllDay,
    @Size(max = 200) String location
  ) {}

  public record ScheduleDeleteVO(
    @NotNull String scheduleId
  ) {}

  public record ScheduleCtgReqVO(
    @NotBlank String name,
    Integer sortOrder,
    @NotNull Integer colorId
  ) {}

  public record ScheduleCtgUpdateVO(
    Long scheduleCtgId,
    String name,
    Integer sortOrder,
    Integer colorId
  ) {}

  public record ScheduleCtgDeleteVO(
    Long scheduleCtgId
  ) {}
}
