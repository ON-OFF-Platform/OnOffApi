package kr.co.onmediagroup.onoffapi.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import kr.co.onmediagroup.onoffapi.config.JWTConfig;
import kr.co.onmediagroup.onoffapi.model.dto.KakaoUserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;

/**
 * Kakap Social Login
 * 토큰 가져오기
 *
 * */
public class KakaoUtil {

  private static final RestTemplate restTemplate = new RestTemplate();

  public static String getAccessToken(String code, String clientId, String redirectUri, String tokenUri) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "authorization_code");
    body.add("client_id", clientId);
    body.add("redirect_uri", redirectUri);
    body.add("code", code);

    HttpEntity<?> entity = new HttpEntity<>(body, headers);
    ResponseEntity<Map> response = restTemplate.postForEntity(tokenUri, entity, Map.class);
    return (String) response.getBody().get("access_token");
  }

  public static KakaoUserInfo.KakaoUserInfoDTO getUserInfo(String accessToken, String userInfoUri) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);
    HttpEntity<?> entity = new HttpEntity<>(headers);

    ResponseEntity<Map> response = restTemplate.exchange(userInfoUri, HttpMethod.GET, entity, Map.class);

    Map<String, Object> body = response.getBody();
    Map<String, Object> account = (Map<String, Object>) body.get("kakao_account");
    Map<String, Object> profile = (Map<String, Object>) account.get("profile");

    return KakaoUserInfo.KakaoUserInfoDTO.builder()
      .id(body.get("id").toString())
      .email((String) account.get("email"))
      .nickname((String) profile.get("nickname"))
      .build();
  }

  public static String createJwt(String userId, Algorithm algorithm, int expiredSeconds) {
    Date now = new Date();
    Date expiry = new Date(now.getTime() + expiredSeconds * 1000L);

    return JWT.create()
      .withSubject(userId)
      .withIssuedAt(now)
      .withExpiresAt(expiry)
      .sign(algorithm);
  }
}
