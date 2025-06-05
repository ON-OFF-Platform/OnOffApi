package kr.co.onmediagroup.onoffapi.model.dto;

import lombok.Builder;
import lombok.Data;

public class KakaoUserInfo {

  @Data
  @Builder
  public class KakaoUserInfoDTO {
    private String id;
    private String email;
    private String nickname;
  }
}
