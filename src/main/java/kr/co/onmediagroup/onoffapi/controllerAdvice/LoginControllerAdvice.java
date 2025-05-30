package kr.co.onmediagroup.onoffapi.controllerAdvice;

import kr.co.onmediagroup.onoffapi.exception.LoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoginControllerAdvice extends BaseControllerAdvice{

  // 로그인 예외 실패 수 있으면, 예외 응답에 실패 수 포함.
  @ExceptionHandler({LoginException.class})
  public ProblemDetail  handleLoginFail(LoginException ex) {
    ProblemDetail problemDetail = this.exceptionResponse(HttpStatus.UNAUTHORIZED, ex);
    if (ex.getLoginFailCount() != null) {
      problemDetail.setProperty("loginFailCount", ex.getLoginFailCount());
    }
    return problemDetail;
  }
}
