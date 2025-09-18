package kr.co.onmediagroup.onoffapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class Color {

  @Builder
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ColorDTO {
    private Integer colorId;
    private String colorCode;
    private String colorName;
  }
}
