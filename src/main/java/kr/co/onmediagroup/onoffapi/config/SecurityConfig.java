package kr.co.onmediagroup.onoffapi.config;

import jakarta.servlet.http.HttpServletResponse;
import kr.co.onmediagroup.onoffapi.filter.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.time.Duration;
import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
  private final JWTAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

    httpSecurity
      .headers(AbstractHttpConfigurer::disable)
      .formLogin(AbstractHttpConfigurer::disable)
      .csrf(AbstractHttpConfigurer::disable)
      .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .cors(corsConfigurer -> corsConfigurer.configurationSource(this.corsConfigurationScource()))
      .exceptionHandling(exception -> exception.authenticationEntryPoint((request, response, authException) -> {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"error\":\"Unauthorized\"}");
      })
    )
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/auth/**").permitAll()
        .anyRequest().authenticated()
      )
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }

  public CorsConfigurationSource corsConfigurationScource() {
    return request -> {
      CorsConfiguration corsConfiguration = new CorsConfiguration();

      String origin = request.getHeader("Origin");
      if (origin != null && !origin.isBlank()) {
        corsConfiguration.addAllowedOrigin(origin);
      }

      corsConfiguration.setAllowedMethods(List.of("POST", "GET", "OPTIONS", "PUT", "PATCH", "DELETE"));
      corsConfiguration.setMaxAge(Duration.ofSeconds(3600));
      corsConfiguration.setAllowedHeaders(List.of("Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization"));
      corsConfiguration.setExposedHeaders(List.of("Content-Length", "Authorization"));
      corsConfiguration.setAllowCredentials(true);

      return corsConfiguration;
    };
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
