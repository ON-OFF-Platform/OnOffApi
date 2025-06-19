package kr.co.onmediagroup.onoffapi.controllerAdvice;

import kr.co.onmediagroup.onoffapi.exception.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 인증 관련 예외를 전역 처리하는 컨트롤러 어드바이스
 * */
@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthControllerAdvice extends BaseControllerAdvice {

  @ExceptionHandler({AuthException.class})
  public ProblemDetail handleAuthException(AuthException ex) {
    return this.exceptionResponse(HttpStatus.UNAUTHORIZED, ex);
  }
}
