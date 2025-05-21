package kr.co.onmediagroup.onoffapi.model.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kr.co.onmediagroup.onoffapi.model.dto.UserDTO;

import java.time.LocalDate;

public class UserVO {

  public record UserReqVO(@NotBlank @Size(min = 2, max = 255) String userName,
                          @NotBlank @Size(min = 2, max = 255) String userPassword,
                          @NotBlank @Size(min = 2, max = 255) String userEmail,
                          LocalDate userBirth,
                          @NotBlank UserDTO.UserAuthType authType,
                          UserDTO.UserSocialProvider socialProvider,
                          String socialId){
  }
}
