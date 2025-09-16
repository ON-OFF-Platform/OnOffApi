package kr.co.onmediagroup.onoffapi.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PlannedExpenses {

  public static class PlannedExpensesDTO {
    private String plannedExpenseId;
    private String scheduleId;
    private String userId;
    private Long expenseCtgId;
    private String title;
    private BigDecimal amount;
    private LocalDate plannedDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
  }
}
