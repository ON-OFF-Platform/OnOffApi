package kr.co.onmediagroup.onoffapi.model.dto;

import lombok.*;

import java.time.LocalDateTime;

public class ExpenseCtg {

  @Getter
  @AllArgsConstructor
  public enum ExpenseType {
    INCOME("INCOME"),
    EXPENSE("EXPENSE");
    private final String type;
  }

  @Builder
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ExpenseCtgDTO {
    private Long expenseCtgId;
    private String userId;
    private String name;
    private ExpenseCtg.ExpenseType type;
    private Integer colorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
  }
}
