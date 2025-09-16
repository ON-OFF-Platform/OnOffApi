package kr.co.onmediagroup.onoffapi.exception;

import lombok.Getter;

/**
 * 일정 관련 예외 처리
 */
@Getter
public class ScheduleException extends RuntimeException {
  public ScheduleException(String message) {super(message);}

  // 일정 없음
  public static class NoSchedule extends ScheduleException {
    public NoSchedule() { super("no schedule"); }
  }

  // 권한 없음
  public static class AccessDenied extends ScheduleException {
    public AccessDenied() { super("access denied"); }
  }

  // 카테고리 없음
  public static class NoScheduleCtg extends ScheduleException {
    public NoScheduleCtg() { super("no schedule categories"); }
  }
}
