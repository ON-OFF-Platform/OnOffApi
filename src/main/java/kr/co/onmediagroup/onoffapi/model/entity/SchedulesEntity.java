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

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "schedules", indexes = {
  @Index(name = "idx__user_id__start_time", columnList = "user_id, start_time", unique = false),
  @Index(name = "idx__schedule_ctg_id", columnList = "schedule_ctg_id", unique = false),
})
@Entity
@EntityListeners(AuditingEntityListener.class)
public class SchedulesEntity {

  @Id
  @UUID
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "schedule_id")
  private String scheduleId;

  @NotBlank
  @Size(max = 255)
  @Column(name = "user_id")
  private String userId;

  @NotNull
  @Column(name = "schedule_ctg_id")
  private Long scheduleCtgId;

  @NotBlank
  @Size(max = 255)
  @Column(name = "content")
  private String content;

  @NotNull
  @Column(name = "start_time")
  private LocalDateTime startTime;

  @NotNull
  @Column(name = "end_time")
  private LocalDateTime endTime;

  @NotNull
  @Column(name = "is_all_day")
  private Boolean isAllDay;

  @Size(max = 200)
  @Column(name = "location")
  private String location;

  @Column(name = "created_at")
  @CreatedDate
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @LastModifiedDate
  private LocalDateTime updatedAt;



  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "schedule_ctg_id", referencedColumnName = "schedule_ctg_id", insertable = false, updatable = false)
  private ScheduleCtgEntity scheduleCtg;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
  private UserEntity user;

}
