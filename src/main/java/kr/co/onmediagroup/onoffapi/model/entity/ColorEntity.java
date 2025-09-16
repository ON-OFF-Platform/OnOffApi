package kr.co.onmediagroup.onoffapi.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "color")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ColorEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "color_id")
  private Integer colorId;

  @NotBlank
  @Size(max = 7)
  @Column(name = "color_code")
  private String colorCode;

  @NotBlank
  @Size(max = 50)
  @Column(name = "color_name")
  private String colorName;
}
