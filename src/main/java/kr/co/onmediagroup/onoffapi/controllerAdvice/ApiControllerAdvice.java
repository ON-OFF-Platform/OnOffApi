package kr.co.onmediagroup.onoffapi.controllerAdvice;

import kr.co.onmediagroup.onoffapi.exception.AlreadyExistException;
import kr.co.onmediagroup.onoffapi.exception.BadRequestException;
import kr.co.onmediagroup.onoffapi.exception.InvalidUserInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * REST API 전용 예외 처리 클래스
 *
 * 컨트롤러에서 발생하는 다양한 예외를 잡아 ProblemDetail 형식으로 응답
 * 커스텀 예외처리
 *
 * */
@RestControllerAdvice
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
public class ApiControllerAdvice extends BaseControllerAdvice {

  // 중복 처리 예외
  @ExceptionHandler({AlreadyExistException.class})
  public ProblemDetail handleAlreadyExistException(AlreadyExistException ex) {
    return this.exceptionResponse(HttpStatus.BAD_REQUEST, ex);
  }

  // 잘못된 요청
  @ExceptionHandler({BadRequestException.class})
  public ProblemDetail handleBadRequestException(BadRequestException ex) {
    return this.exceptionResponse(HttpStatus.BAD_REQUEST, ex);
  }

  // 요청 바디가 잘못된 경우
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ProblemDetail handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
    return this.exceptionResponse(HttpStatus.BAD_REQUEST, "");
  }

  // 비어있는 필드
  @ExceptionHandler(IllegalArgumentException.class)
  public ProblemDetail handleIllegalArgumentException(IllegalArgumentException ex) {
    return this.exceptionResponse(HttpStatus.BAD_REQUEST, ex);
  }








  // 그 외
  @ExceptionHandler(Exception.class)
  public ProblemDetail handlerException(Exception ex) {
    return this.exceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, new Exception("unknown server error"));
  }


  /**
   * 유효성 검사 실패 처리
   *
   * @Valid 어노테이션 등에서 발생한 필드 검증 오류 메시지 맵 형태로 반환
   * 필드명과 메시지를 fieldMap 프로퍼티에 포함시켜 클라이언트에게 상세 정보 제공
   *
   * */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {
    Map<String, Object> fieldMap = new HashMap<>();

    for (ObjectError error : ex.getBindingResult().getAllErrors()) {
      String fieldName = ((FieldError) error).getField();
      String fieldMessage = error.getDefaultMessage();

      if (fieldName == null) {
        fieldMessage = "request parameter invalid value";
      }
      if (fieldMessage.startsWith("Failed to convert")) {
        fieldMessage = "request parameter invalid value";
      }
      fieldMap.put(fieldName, fieldMessage);
    }

    InvalidUserInputException invalidUserInputException = new InvalidUserInputException(fieldMap);
    ProblemDetail problemDetail = this.exceptionResponse(HttpStatus.BAD_REQUEST, invalidUserInputException);
    problemDetail.setProperty("fieldMap", fieldMap);
    return problemDetail;
  }

}
