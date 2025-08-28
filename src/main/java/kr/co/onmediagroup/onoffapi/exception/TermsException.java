package kr.co.onmediagroup.onoffapi.exception;

import lombok.Getter;

/**
 * 약관 동의 관련 예외 처리
 * */
@Getter
public class TermsException extends RuntimeException {
  public TermsException(String message) {super(message);}

  // 약관 동의 없음
  public static class NoTerms extends TermsException {
    public NoTerms() { super("no terms"); }
  }
}
