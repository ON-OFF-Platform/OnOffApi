package kr.co.onmediagroup.onoffapi.config;

import kr.co.onmediagroup.onoffapi.util.AuditorAwareImple;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JPAConfig {

  // 작성자 정보를 제공하는 구현체
  @Bean
  AuditorAware<String> auditorProvider() { return new AuditorAwareImple(); }
}
