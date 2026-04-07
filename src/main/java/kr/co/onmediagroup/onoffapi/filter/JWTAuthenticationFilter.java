package kr.co.onmediagroup.onoffapi.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import kr.co.onmediagroup.onoffapi.exception.AuthException;
import kr.co.onmediagroup.onoffapi.exception.CustomAuthenticationEntryPoint;
import kr.co.onmediagroup.onoffapi.model.dto.User;
import kr.co.onmediagroup.onoffapi.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT 인증 커스텀 필터
 *
 * HTTP 요청의 Authorization 헤더에서 JWT를 추출하고 유효성 검증을 수행합니다.
 * 유효한 토큰인 경우 SecurityContext에 인증 정보를 저장합니다.
 * 인증 실패 또는 토큰이 없을 경우, 인증 예외는 CustomAuthenticationEntryPoint에서 처리됩니다.
 * OncePerRequestFilter를 상속하여 요청당 한 번만 필터가 실행됩니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
  private final JWTUtil jwtUtil;

  @Override
  protected void doFilterInternal(
    @NotNull HttpServletRequest request,
    @NotNull HttpServletResponse response,
    @NotNull FilterChain filterChain
  ) throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");

    try {
      // header null X, Bearer ...
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String token = authHeader.substring(7);
        log.info("isToken: {}", jwtUtil.verifyToken(token));

        // 토큰 검증
        if (jwtUtil.verifyToken(token)) {

          // JWT 사용자 정보 추출
          User.UserPrincipal principal = jwtUtil.verifyTokenWithUserPrincipal(token);

          // 사용자 정보 있는 경우만 설정
          if (principal != null && principal.getUserLevel() != null) {
            UsernamePasswordAuthenticationToken authentication =
              new UsernamePasswordAuthenticationToken(
                principal,
                null,
                principal.getAuthorities()
              );

            // securityContext 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("securityContext: {}", SecurityContextHolder.getContext().getAuthentication());

          }
        }

      }

      // 다음 필터(또는 최종 컨트롤러) 로 요청을 전달
      filterChain.doFilter(request, response);

    } catch (AuthException ex) {
      // 예외 처리
      SecurityContextHolder.clearContext();
      customAuthenticationEntryPoint.commence(request, response, ex);
    }
  }
}
