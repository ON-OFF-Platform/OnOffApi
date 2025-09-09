package kr.co.onmediagroup.onoffapi.email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class EmailDTO {
  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class EmailReqVO{
    @NotBlank
    @Email
    private String email;
  }

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class EmailResVO{
    private String email;
    private String code;
  }

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class EmailVerifyReqVO{
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String code;
  }
}
