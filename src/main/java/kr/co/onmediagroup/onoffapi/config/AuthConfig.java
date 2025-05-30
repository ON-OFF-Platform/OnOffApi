package kr.co.onmediagroup.onoffapi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AuthConfig {
  private final Integer maxLoginFailCount;

  public AuthConfig(@Value("${auth.max-login-fail-count}") Integer maxLoginFailCount) {
    this.maxLoginFailCount = maxLoginFailCount;
  }
}
