package kr.co.onmediagroup.onoffapi.service;

import jakarta.transaction.Transactional;
import kr.co.onmediagroup.onoffapi.exception.AlreadyExistException;
import kr.co.onmediagroup.onoffapi.exception.BadRequestException;
import kr.co.onmediagroup.onoffapi.model.dto.User;
import kr.co.onmediagroup.onoffapi.model.entity.UserEntity;
import kr.co.onmediagroup.onoffapi.repository.UserRepository;
import kr.co.onmediagroup.onoffapi.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static kr.co.onmediagroup.onoffapi.model.ModelConverter.MODEL_MAPPER;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public User.UserResponse createUser(
    String name,
    String password,
    String email,
    User.UserAdYn adYn,
    LocalDate userBirth,
    User.UserAuthType authType,
    User.UserSocialProvider socialProvider,
    String socialId
  ) {
    // 이메일 중복 체크
    if (userRepository.findById(email).isPresent()) {
      throw new AlreadyExistException("already exist email");
    }

    // 소셜 ID 중복 체크
    if (socialId != null && userRepository.findBySocialId(socialId).isPresent()) {
      throw new AlreadyExistException("already exist social id");
    }

    // 일반 로그인 시, 비밀번호는 반드시 있어야 함.
    if (socialProvider == null && !StringUtil.isExist(password)) {
      throw new BadRequestException("password not null");
    }

    // 소셜 로그인 시, 비밀번호 있으면 안됨.
    if (socialProvider != null && StringUtil.isExist(password)) {
      throw new BadRequestException("password not allowed");
    }

    UserEntity userEntity = UserEntity.builder()
      .userId(email)
      .userName(name)
      .userPassword(passwordEncoder.encode(password))
      .userEmail(email)
      .adYn(adYn)
      .userLevel(User.UserLevel.USER)
      .activeYn(User.UserActiveYn.Y)
      .loginFailCount(0)
      .userBirth(userBirth)
      .authType(authType)
      .socialProvider(socialProvider)
      .socialId(socialId)
      .build();

    userRepository.save(userEntity);

    User.UserResponse userResponse = MODEL_MAPPER.map(userEntity, User.UserResponse.class);
    return userResponse;
  }
}