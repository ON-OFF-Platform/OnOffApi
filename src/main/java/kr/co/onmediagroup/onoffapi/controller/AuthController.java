package kr.co.onmediagroup.onoffapi.controller;

import jakarta.validation.Valid;
import kr.co.onmediagroup.onoffapi.model.vo.UserVO;
import kr.co.onmediagroup.onoffapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;

  @PostMapping("/login")
  @ResponseStatus(value = HttpStatus.OK)
  public UserVO.LoginResVO login(
    @Valid @RequestBody UserVO.LoginReqVO loginReqVO
  ) {
    return this.authService.login(loginReqVO);
  }
}
