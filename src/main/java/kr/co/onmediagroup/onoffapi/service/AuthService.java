package kr.co.onmediagroup.onoffapi.service;

import jakarta.transaction.Transactional;
import kr.co.onmediagroup.onoffapi.exception.AlreadyException;
import kr.co.onmediagroup.onoffapi.exception.BadRequestException;
import kr.co.onmediagroup.onoffapi.model.dto.UserDTO;
import kr.co.onmediagroup.onoffapi.model.entity.UserEntity;
import kr.co.onmediagroup.onoffapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static kr.co.onmediagroup.onoffapi.model.ModelConverter.MODEL_MAPPER;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;

  public UserDTO.UserResDTO createUser(
    String name,
    String password,
    String email,
    LocalDate userBirth,
    UserDTO.UserAuthType authType,
    UserDTO.UserSocialProvider socialProvider,
    String socialId
  ) {
    // 이메일 중복 체크
    if (userRepository.findById(email).isPresent()) {
      throw new AlreadyException("already exist email");
    }

    // 소셜 ID 중복 체크
    if (socialId != null && userRepository.findBySocialId(socialId).isPresent()) {
      throw new AlreadyException("already exist social id");
    }

    // 일반 로그인 시, 비밀번호 빈값 허용 안됨
    if (socialProvider == null && (password == null || password.isEmpty())) {
      throw new BadRequestException("not null password");
    }

    // 소셜 로그인 시, 비밀번호 입력 안됨
    if (socialProvider != null && password != null && !password.isEmpty()) {
      throw new BadRequestException("not allowed password");
    }

    UserEntity userEntity = UserEntity.builder()
      .userId(email)
      .userName(name)
      .userPassword(password) // passwordEncoder.encode(password)
      .userEmail(email)
      .userLevel(UserDTO.UserLevel.USER)
      .activeYn(UserDTO.UserActiveYn.Y)
      .userBirth(userBirth)
      .authType(authType)
      .socialProvider(socialProvider)
      .socialId(socialId)
      .build();

    userRepository.save(userEntity);

    UserDTO.UserResDTO userResDTO = MODEL_MAPPER.map(userEntity, UserDTO.UserResDTO.class);
    return userResDTO;
  }
}
