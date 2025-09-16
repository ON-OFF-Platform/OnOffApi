package kr.co.onmediagroup.onoffapi.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.co.onmediagroup.onoffapi.model.dto.ExpenseCtg;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "expense_ctg", indexes = {
  @Index(name = "idx__user_id", columnList = "user_id", unique = false)
})
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ExpenseCtgEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "expense_ctg_id")
  private Long expenseCtgId;

  @NotBlank
  @Size(max = 255)
  @Column(name = "user_id")
  private String userId;

  @NotBlank
  @Size(max = 50)
  @Column(name = "name")
  private String name;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private ExpenseCtg.ExpenseType type = ExpenseCtg.ExpenseType.EXPENSE;

  @NotNull
  @Column(name = "color_id")
  private Integer colorId;

  @Column(name = "created_at")
  @CreatedDate
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @LastModifiedDate
  private LocalDateTime updatedAt;



  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "color_id", referencedColumnName = "color_id", insertable = false, updatable = false)
  private ColorEntity color;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
  private UserEntity user;
}
