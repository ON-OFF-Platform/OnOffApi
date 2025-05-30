package kr.co.onmediagroup.onoffapi.config;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class JWTConfig {
  private final int expiredSeconds;
  private final Algorithm algorithm;

  public JWTConfig(@Value("${auth.jwt.signKey}") String signKey,
                   @Value("${auth.jwt.expiredSeconds}") int expiredSeconds) {

    this.expiredSeconds = expiredSeconds;
    this.algorithm = Algorithm.HMAC256(signKey.getBytes());
  }
}
