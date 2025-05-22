package kr.co.onmediagroup.onoffapi.controllerAdvice;

import kr.co.onmediagroup.onoffapi.exception.AlreadyExistException;
import kr.co.onmediagroup.onoffapi.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
public class ApiControllerAdvice extends BaseControllerAdvice {

  @ExceptionHandler({AlreadyExistException.class})
  public ProblemDetail handleAlreadyExistException(AlreadyExistException ex) {
    return this.exceptionResponse(HttpStatus.BAD_REQUEST, ex);
  }

  @ExceptionHandler({BadRequestException.class})
  public ProblemDetail handleBadRequestException(BadRequestException ex) {
    return this.exceptionResponse(HttpStatus.BAD_REQUEST, ex);
  }

  @ExceptionHandler(Exception.class)
  public ProblemDetail handlerException(Exception ex) {
    return this.exceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, new Exception("unknown server error"));
  }
}
