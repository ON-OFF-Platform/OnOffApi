package kr.co.onmediagroup.onoffapi.util;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImple implements AuditorAware<String> {
  @Override
  public Optional<String> getCurrentAuditor() {
    return Optional.empty();
  }
}
