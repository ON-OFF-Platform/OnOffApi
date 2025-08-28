package kr.co.onmediagroup.onoffapi.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_terms_agree", indexes = {
  @Index(name = "idx__user_id", columnList = "user_id", unique = false),
  @Index(name = "unq__user_id__terms_id", columnList = "user_id, terms_id", unique = true)
})
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserTermsAgreeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_terms_agree_id")
  private Long userTermsAgreeId;

  @NotBlank
  @Size(max = 255)
  @Column(name = "user_id")
  private String userId;

  @NotNull
  @Column(name = "terms_id")
  private Integer termsId;

  @NotNull
  @Column(name = "agreed", columnDefinition = "TINYINT(1)")
  private Boolean agreed;

  @Column(name = "created_at")
  @CreatedDate
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @LastModifiedDate
  private LocalDateTime updatedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "terms_id", referencedColumnName = "terms_id", insertable = false, updatable = false)
  private TermsEntity terms;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
  private UserEntity user;
}
