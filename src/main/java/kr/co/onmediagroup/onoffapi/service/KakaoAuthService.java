package kr.co.onmediagroup.onoffapi.service;

import com.auth0.jwt.JWT;
import jakarta.transaction.Transactional;
import kr.co.onmediagroup.onoffapi.config.JWTConfig;
import kr.co.onmediagroup.onoffapi.model.dto.KakaoUserInfo;
import kr.co.onmediagroup.onoffapi.model.dto.User;
import kr.co.onmediagroup.onoffapi.model.entity.UserEntity;
import kr.co.onmediagroup.onoffapi.repository.UserRepository;
import kr.co.onmediagroup.onoffapi.util.KakaoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class KakaoAuthService {

  @Value("${auth.kakao.client-id}")
  private String clientId;
  @Value("${auth.kakao.redirect-uri}")
  private String redirectUri;
  @Value("${auth.kakao.token-uri}")
  private String tokenUri;
  @Value("${auth.kakao.user-info-uri}")
  private String userInfoUri;

  private final UserRepository userRepository;
  private final JWTConfig jwtConfig;

  public String loginWithKakao(String code) {
    String accessToken = KakaoUtil.getAccessToken(code, clientId, redirectUri, tokenUri);
    KakaoUserInfo.KakaoUserInfoDTO userInfo = KakaoUtil.getUserInfo(accessToken, userInfoUri);

    // 이미 가입된 유저인지 확인 (소셜 ID + Provider 기준)
    Optional<UserEntity> optionalUser = userRepository.findBySocialIdAndSocialProvider(
      userInfo.getId(), User.UserSocialProvider.KAKAO);

    UserEntity user = optionalUser.orElseGet(() -> {
      UserEntity newUser = UserEntity.builder()
        .userId(userInfo.getEmail())
        .userName(userInfo.getNickname())
        .userEmail(userInfo.getEmail())
        .userLevel(User.UserLevel.USER)
        .authType(User.UserAuthType.S)
        .socialId(userInfo.getId())
        .socialProvider(User.UserSocialProvider.KAKAO)
        .activeYn(User.UserActiveYn.Y)
        .loginFailCount(0)
        .build();
      return userRepository.save(newUser);
    });

    return KakaoUtil.createJwt(userInfo.getEmail(), jwtConfig.getAlgorithm(), jwtConfig.getExpiredSeconds());
  }
}
