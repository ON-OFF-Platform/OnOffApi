package kr.co.onmediagroup.onoffapi.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.co.onmediagroup.onoffapi.model.dto.Terms;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "terms", indexes = {
  @Index(name = "idx__type__version", columnList = "type, version", unique = false)
})
@Entity
@EntityListeners(AuditingEntityListener.class)
public class TermsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "terms_id")
  private Integer termsId;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private Terms.TermsType type;

  @NotBlank
  @Size(max = 255)
  @Column(name = "title")
  private String title;

  @Lob
  @NotBlank
  @Column(name = "content")
  private String content;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "required")
  private Terms.TermsRequired required = Terms.TermsRequired.Y;

  @NotNull
  @Column(name = "version")
  private LocalDate version;

  @Column(name = "created_at")
  @CreatedDate
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @LastModifiedDate
  private LocalDateTime updatedAt;
}
