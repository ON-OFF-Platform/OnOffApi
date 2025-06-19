package kr.co.onmediagroup.onoffapi.exception;

import lombok.Getter;

import java.util.Map;

/**
 * 사용자 입력값 예외 처리
 * */
@Getter
public class InvalidUserInputException extends RuntimeException {
  private final Map<String, Object> fieldMap;

  public InvalidUserInputException(Map<String, Object> fieldMap) {
    super("invalid user input. check field");
    this.fieldMap = Map.copyOf(fieldMap);
  }
}
