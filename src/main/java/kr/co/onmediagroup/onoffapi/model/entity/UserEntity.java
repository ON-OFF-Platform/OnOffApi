package kr.co.onmediagroup.onoffapi.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.co.onmediagroup.onoffapi.model.dto.User;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class UserEntity {

  @Id
  @Size(max = 255)
  @Column(name = "user_id")
  private String userId;

  @NotBlank
  @Size(max = 255)
  @Column(name = "user_name")
  private String userName;

  @Size(max = 255)
  @Column(name = "user_password")
  private String userPassword;

  @NotBlank
  @Size(max = 255)
  @Column(name = "user_email")
  private String userEmail;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "user_level")
  private User.UserLevel userLevel = User.UserLevel.USER;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "active_yn")
  private User.UserActiveYn activeYn = User.UserActiveYn.Y;

  @NotNull
  @Column(name = "login_fail_count")
  private Integer loginFailCount = 0;

  @Column(name = "user_birth")
  private LocalDate userBirth;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "auth_type")
  private User.UserAuthType authType = User.UserAuthType.N;

  @Enumerated(EnumType.STRING)
  @Column(name = "social_provider")
  private User.UserSocialProvider socialProvider;

  @Size(max = 255)
  @Column(name = "social_id")
  private String socialId;

  @Column(name = "created_at")
  @CreatedDate
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @LastModifiedDate
  private LocalDateTime updatedAt;

  // 로그인 실패 기록 초기화
  public UserEntity clearFailedLogin() {
    this.loginFailCount = 0;
    return this;
  }

  // 로그인 실패 기록 증가. 최대 실패 횟수 이상 실패 시, 사용자 비활성화
  public UserEntity increaseFailedLogin(Integer maxFailedLoginCount) {
    this.loginFailCount = this.loginFailCount + 1;
    if (loginFailCount >= maxFailedLoginCount) {
      this.activeYn = User.UserActiveYn.N;
    }
    return this;
  }
}
