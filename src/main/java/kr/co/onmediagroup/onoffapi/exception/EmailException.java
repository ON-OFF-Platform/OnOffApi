package kr.co.onmediagroup.onoffapi.exception;

import lombok.Getter;

@Getter
public class EmailException extends RuntimeException{
  public EmailException(String message) {super(message);}

  // 인증코드 전송 실패
  public static class FailedEmailCode extends EmailException {
    public FailedEmailCode() { super("Failed to send email verification code");}
  }
}
