package kr.co.onmediagroup.onoffapi.controller;

import jakarta.validation.Valid;
import kr.co.onmediagroup.onoffapi.model.dto.User;
import kr.co.onmediagroup.onoffapi.model.vo.UserVO;
import kr.co.onmediagroup.onoffapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/user")
public class UserController {
  private final UserService userService;

  @PostMapping("/join")
  @ResponseStatus(value = HttpStatus.CREATED)
  public User.UserResDTO join(
    @Valid @RequestBody UserVO.UserReqVO userReqVO
  ) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // VO -> DTO
    User.UserResDTO User = this.userService.createUser(
      userReqVO.userName(),
      userReqVO.userPassword(),
      userReqVO.userEmail(),
      userReqVO.adYn(),
      userReqVO.userBirth(),
      userReqVO.authType(),
      userReqVO.socialProvider(),
      userReqVO.socialId()
    );

    return User;
  }
}
