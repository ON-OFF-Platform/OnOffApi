package kr.co.onmediagroup.onoffapi.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "planned_expenses", indexes = {
  @Index(name = "idx__user_id__planned_date", columnList = "user_id, planned_date", unique = false),
  @Index(name = "idx__schedule_id", columnList = "schedule_id", unique = false),
  @Index(name = "idx__expense_ctg_id", columnList = "expense_ctg_id", unique = false),
})
@Entity
@EntityListeners(AuditingEntityListener.class)
public class PlannedExpensesEntity {

  @Id
  @UUID
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "planned_expense_id")
  private String plannedExpenseId;

  @NotBlank
  @Size(max = 255)
  @Column(name = "schedule_id")
  private String scheduleId;

  @NotBlank
  @Size(max = 255)
  @Column(name = "user_id")
  private String userId;

  @NotNull
  @Column(name = "expense_ctg_id")
  private Long expenseCtgId;

  @NotBlank
  @Size(max = 100)
  @Column(name = "title")
  private String title;

  @Builder.Default
  @NotNull
  @Column(name = "amount")
  private BigDecimal amount = BigDecimal.ZERO;

  @NotNull
  @Column(name = "planned_date")
  private LocalDate plannedDate;

  @Column(name = "created_at")
  @CreatedDate
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @LastModifiedDate
  private LocalDateTime updatedAt;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "schedule_id", referencedColumnName = "schedule_id", insertable = false, updatable = false)
  private SchedulesEntity schedules;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "expense_ctg_id", referencedColumnName = "expense_ctg_id", insertable = false, updatable = false)
  private ExpenseCtgEntity expenseCtg;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
  private UserEntity user;
}
