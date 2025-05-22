package kr.co.onmediagroup.onoffapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class UserDTO {

  @Getter
  @AllArgsConstructor
  public enum UserLevel{
    ADMIN("ADMIN"),
    USER("USER");
    private final String level;
  }

  @Getter
  @AllArgsConstructor
  public enum UserActiveYn{
    Y("Y"),
    N("N");
    private final String active;
  }

  @Getter
  @AllArgsConstructor
  public enum UserAuthType{
    N("N"),
    S("S");
     private final String type;
  }

  @Getter
  @AllArgsConstructor
  public enum UserSocialProvider{
    KAKAO("KAKAO"),
    GOOGLE("GOOGLE");
    private final String provider;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class UserReqDTO{
    private String userId;
    private String userName;
    private String userPassword;
    private String userEmail;
    private LocalDate userBirth;
    private UserDTO.UserAuthType authType;
    private UserDTO.UserSocialProvider socialProvider;
    private String socialId;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class UserResDTO{
    private String userName;
    private String userEmail;
    private UserDTO.UserLevel userLevel;
    private UserDTO.UserActiveYn activeYn;
    private LocalDate userBirth;
  }
}
