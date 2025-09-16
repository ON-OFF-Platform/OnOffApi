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
@Table(name = "expenses", indexes = {
  @Index(name = "idx__user_id__expense_date", columnList = "user_id, expense_date", unique = false),
  @Index(name = "idx__expense_ctg_id", columnList = "expense_ctg_id", unique = false)
})
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ExpensesEntity {

  @Id
  @UUID
  @GeneratedValue(strategy = GenerationType.UUID)
  @Size(max = 255)
  @Column(name = "expense_id")
  private String expenseId;

  @NotBlank
  @Size(max = 255)
  @Column(name = "user_id")
  private String userId;

  @NotNull
  @Column(name = "expense_ctg_id")
  private Long expenseCtgId;

  @Builder.Default
  @Column(name = "amount")
  private BigDecimal amount = BigDecimal.ZERO;

  @NotNull
  @Column(name = "expense_date")
  private LocalDate expenseDate;

  @Column(name = "description")
  private String description;

  @Column(name = "created_at")
  @CreatedDate
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @LastModifiedDate
  private LocalDateTime updatedAt;



  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "expense_ctg_id", referencedColumnName = "expense_ctg_id", insertable = false, updatable = false)
  private ExpenseCtgEntity expenseCtg;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
  private UserEntity user;
}
