package kr.co.onmediagroup.onoffapi.model.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.co.onmediagroup.onoffapi.model.dto.User;
import lombok.Builder;

import java.time.LocalDate;

public class UserVO {

  public record UserReqVO(
    @NotBlank @Size(min = 2, max = 255) String userName,
    @Size(max = 255) String userPassword,
    @NotBlank @Size(min = 2, max = 255) String userEmail,
    User.UserAdYn adYn,
    LocalDate userBirth,
    @NotNull User.UserAuthType authType,
    User.UserSocialProvider socialProvider,
    String socialId
  ) {}

  public record LoginReqVO(
    @NotBlank @Size(min = 2, max = 255) String userId,
    @NotBlank @Size(max = 255) String password
  ) {}

  @Builder
  public record LoginResVO(
    String token,
    User.UserResDTO user
  ) {}
}
