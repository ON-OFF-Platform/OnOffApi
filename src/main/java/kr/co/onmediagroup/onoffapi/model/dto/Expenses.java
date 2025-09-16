package kr.co.onmediagroup.onoffapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Expenses {

  @Builder
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ExpensesDTO {
    private String expenseId;
    private String userId;
    private Long expenseCtgId;
    private BigDecimal amount;
    private LocalDate expenseDate;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
  }
}
