package kr.co.onmediagroup.onoffapi.util;

import kr.co.onmediagroup.onoffapi.config.JWTConfig;
import kr.co.onmediagroup.onoffapi.model.dto.User;
import kr.co.onmediagroup.onoffapi.model.entity.UserEntity;
import org.junit.jupiter.api.*;

import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JWTUtilTest {
  private final JWTUtil jwtUtil;
  private final String testUserId = "test-user-id";
  private final Map<String, Object> userClaimMap = Map.of("userLevel", "test-user-level",
    "activeYn", "Y", "userName", "test-user-name") ;
  private UserEntity user;
  private String token = null;

  JWTUtilTest() {
    JWTConfig jwtConfig = new JWTConfig("test-sign-key", 60);
    this.jwtUtil = new JWTUtil(jwtConfig);

    user = UserEntity.builder()
      .userId("test-user-id")
      .userLevel(User.UserLevel.ADMIN)
      .activeYn(User.UserActiveYn.Y)
      .build();
  }

  @Test
  @Order(0)
  @DisplayName("jwt create")
  void createToken() {
    this.token = this.jwtUtil.createToken(this.user);
    System.out.println("token : " + this.token);

    Assertions.assertNotNull(this.token);
  }

  @Test
  @Order(1)
  @DisplayName("jwt 검증")
  void verifyToken() {
    Assertions.assertTrue(this.token.length() > 0);

    boolean succeedVerify = this.jwtUtil.verifyToken(this.token);
    System.out.println("succeedVerify : " + succeedVerify);
    Assertions.assertTrue(succeedVerify);

    String invalidToken = this.token + "a";
    System.out.println("invalidToken : " + invalidToken);

    boolean failedVerified = this.jwtUtil.verifyToken(invalidToken);
    System.out.println("failedVerified : " + failedVerified);
    Assertions.assertFalse(failedVerified);
  }

  @Test
  @Order(2)
  @DisplayName("jwt 검증 및 유저 정보 확인")
  void verifyTokenWithUserPrincipal() {
    Assertions.assertTrue(this.token.length() > 0);

    User.UserPrincipal userPrincipal = this.jwtUtil.verifyTokenWithUserPrincipal(this.token);
    System.out.println("userPrincipal : " + userPrincipal.getUserId());
    Assertions.assertNotNull(userPrincipal);

    Assertions.assertNotNull(userPrincipal);
    Assertions.assertEquals(user.getUserId(), userPrincipal.getUserId());
    Assertions.assertEquals(user.getUserLevel(), userPrincipal.getUserLevel());
    Assertions.assertEquals(user.getActiveYn(), userPrincipal.getActiveYn());
  }
}
