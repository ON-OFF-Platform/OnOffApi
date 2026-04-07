package kr.co.onmediagroup.onoffapi.controller;

import kr.co.onmediagroup.onoffapi.service.KakaoAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/kakao")
public class KakaoAuthController {
  private final KakaoAuthService kakaoAuthService;

  @GetMapping("/callback")
  public ResponseEntity<?> kakaoCallBack(
    @RequestParam("code") String code
  ) {
    String token = kakaoAuthService.loginWithKakao(code);
    return ResponseEntity.ok(Collections.singletonMap("token", token));
  }
}
