package kr.co.onmediagroup.onoffapi.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;

public class User {

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
    private User.UserAuthType authType;
    private User.UserSocialProvider socialProvider;
    private String socialId;
  }

  @Builder
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class UserResDTO{
    private String userName;
    private String userEmail;
    private User.UserLevel userLevel;
    private User.UserActiveYn activeYn;
    private LocalDate userBirth;
  }


  /**
   * JWT 사용자 정보 DTO
   */
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class MinimumUserPrincipal {
    private String userId;
    private User.UserLevel userLevel;
  }

  public static class UserPrincipal extends MinimumUserPrincipal implements UserDetails {

    @Builder
    public UserPrincipal(String userId, User.UserLevel userLevel, User.UserActiveYn activeYn) {
      super(userId, userLevel);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return null; }

    @Override
    public String getPassword() { return null; }

    @Override
    public String getUsername() { return null; }

    @Override
    public boolean isAccountNonExpired() { return false; }

    @Override
    public boolean isAccountNonLocked() { return false; }

    @Override
    public boolean isCredentialsNonExpired() { return false; }

    @Override
    public boolean isEnabled() { return false; }
  }
}
