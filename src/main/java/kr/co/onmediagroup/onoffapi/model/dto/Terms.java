package kr.co.onmediagroup.onoffapi.model.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Terms {

  @Getter
  @AllArgsConstructor
  public enum TermsType {
    SERVICE("SERVICE"),
    PRIVACY("PRIVACY"),
    MARKETING("MARKETING");
    private final String type;
  }

  @Getter
  @AllArgsConstructor
  public enum TermsRequired {
    Y("Y"),
    N("N");
    private final String required;
  }

  @Builder
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class TermsReqest{
    private Integer termsId;
    private Terms.TermsType type;
    private String title;
    private String content;
    private Terms.TermsRequired required;
    private LocalDate version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
  }
}
