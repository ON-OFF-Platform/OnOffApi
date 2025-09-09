package kr.co.onmediagroup.onoffapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class UserTermsAgree {

  @Builder
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class UserTermsAgreeDTO{
    private Integer userTermsAgreeId;
    private String userId;
    private Integer termsId;
    private Boolean agreed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
  }
}
