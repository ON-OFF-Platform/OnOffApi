package kr.co.onmediagroup.onoffapi.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringUtil {

  // 문자열 null 공백 체크
  public static boolean isExist(String givenString) {
    if (givenString == null) { // check null
      return false;
    }
    if (givenString.isBlank()) { // " " blank check
      return false;
    }
    return true;
  }

}
