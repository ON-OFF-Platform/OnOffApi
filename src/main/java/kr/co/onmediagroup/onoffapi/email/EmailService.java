package kr.co.onmediagroup.onoffapi.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kr.co.onmediagroup.onoffapi.exception.AlreadyExistException;
import kr.co.onmediagroup.onoffapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {
  private final JavaMailSender mailSender;
  private final RedisTemplate<String, String> redisTemplate;
  private final UserRepository userRepository;
  private static final long CODE_TTL = 180; // 5분

  public String createCode() {
    Random random = new Random();
    int code = 100000 + random.nextInt(900000); // 6자리
    return String.valueOf(code);
  }

  // 인증코드 발급
  public EmailDTO.EmailResVO sendVerificationMail(String email) throws MessagingException {
    // 이메일 중복 체크
    if (userRepository.findById(email).isPresent()) {
      throw new AlreadyExistException("already exist email");
    }

    // 난수 발생
    String code = createCode();

    // 전송 이메일 디자인
    String htmlContent = "<html lang=\"ko\">" +
      "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; margin:0; padding:0;\">" +
      "<div style=\"background-color:#ffffff; width:600px; margin:30px auto; padding:30px; border-radius:10px; box-shadow:0 4px 15px rgba(0,0,0,0.1); text-align:center;\">" +
      "<h1 style=\"color:#86BD98; margin-bottom:20px;\">ON_OFF 인증번호 안내</h1>" +
      "<p style=\"font-size:16px; color:#333333;\">아래 인증번호를 5분 이내에 입력해주세요.</p>" +
      "<div style=\"display:inline-block; margin:20px 0; padding:15px 25px; font-size:24px; font-weight:bold; background-color:#f0f0f0; border-radius:8px; letter-spacing:4px; color:#86BD98;\">" +
      code +
      "</div>" +
      "<p style=\"font-size:12px; color:#999999; margin-top:30px;\">본 메일은 발신 전용입니다. 회신되지 않습니다.</p>" +
      "</div>" +
      "</body>" +
      "</html>";

    // 이매일 전송
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

    helper.setTo(email);
    helper.setSubject("ON_OFF 인증번호 안내");
    helper.setText(htmlContent, true);

    mailSender.send(message);

    // 인증코드 이메일과 함께 redis에 저장, 5분
    redisTemplate.opsForValue().set(email, code, CODE_TTL, TimeUnit.SECONDS);

    EmailDTO.EmailResVO emailResVO = EmailDTO.EmailResVO.builder()
      .email(email)
      .code(code)
      .build();

    return emailResVO;
  }

  // 인증 코드 확인
  public boolean verifyCode(String email, String code) {
    // 인증코드 확인
    String savedCode = redisTemplate.opsForValue().get(email);

    if (savedCode == null) return false;

    // 확인 완료되면 인증코드 삭제
    if (savedCode.equals(code)) {
      redisTemplate.delete(email);
      return true;
    }

    return false;
  }
}
