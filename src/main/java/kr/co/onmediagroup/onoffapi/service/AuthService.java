package kr.co.onmediagroup.onoffapi.service;

import jakarta.transaction.Transactional;
import kr.co.onmediagroup.onoffapi.config.AuthConfig;
import kr.co.onmediagroup.onoffapi.exception.LoginException;
import kr.co.onmediagroup.onoffapi.model.dto.User;
import kr.co.onmediagroup.onoffapi.model.entity.UserEntity;
import kr.co.onmediagroup.onoffapi.model.vo.UserVO;
import kr.co.onmediagroup.onoffapi.repository.UserRepository;
import kr.co.onmediagroup.onoffapi.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final AuthConfig authConfig;
  private final PasswordEncoder passwordEncoder;
  private final JWTUtil jwtUtil;

  public UserVO.LoginResVO login(UserVO.LoginReqVO loginReqVO) {
    String userId = loginReqVO.userId();
    String password = loginReqVO.password();

    // 유저 확인
    UserEntity userEntity = this.userRepository.findById(userId)
      .orElseThrow(LoginException.NoUser::new);

    // 로그인 실패 횟수 확인
    Integer maxLoginFailCount = this.authConfig.getMaxLoginFailCount();
    Integer currentLoginFailCount = userEntity.getLoginFailCount();

    if (currentLoginFailCount > maxLoginFailCount) {
      throw new LoginException.TooManyFailedLogin(currentLoginFailCount);
    }

    // 비활성화 유저 확인
    User.UserActiveYn userActiveYn = userEntity.getActiveYn();
    if (userActiveYn != User.UserActiveYn.Y) {
      throw new LoginException.DeactivatedUser(currentLoginFailCount);
    }

    // 비밀번호 확인
    String userPwHash = userEntity.getUserPassword();
    boolean isMatchedPwd = this.passwordEncoder.matches(password, userPwHash);

    if (!isMatchedPwd) {
      userEntity = userEntity.increaseFailedLogin(maxLoginFailCount);
      userEntity = this.userRepository.save(userEntity);
      throw new LoginException.InvalidPassword(currentLoginFailCount);
    }

    // 로그인 성공 시, 로그인 실패 횟수 초기화
    userEntity = userEntity.clearFailedLogin();
    userEntity = this.userRepository.save(userEntity);

    // 토큰 발급
    String token = this.jwtUtil.createToken(userEntity);

    User.UserResDTO userResDTO = User.UserResDTO.builder()
      .userName(userEntity.getUserName())
      .userEmail(userEntity.getUserEmail())
      .userLevel(userEntity.getUserLevel())
      .activeYn(userEntity.getActiveYn())
      .userBirth(userEntity.getUserBirth())
      .build();

    return UserVO.LoginResVO.builder()
      .token(token)
      .userResDTO(userResDTO)
      .build();
  }

}
