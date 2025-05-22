package kr.co.onmediagroup.onoffapi.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * 중복 예외 처리
 * */
@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@NoArgsConstructor
public class AlreadyExistException extends RuntimeException {
  private String message;

  public AlreadyExistException(String message) {
    super(message);
    this.message = message;
  }
}
