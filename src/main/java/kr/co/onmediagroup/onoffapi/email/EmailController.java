package kr.co.onmediagroup.onoffapi.email;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import kr.co.onmediagroup.onoffapi.exception.EmailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/email")
public class EmailController {
  private final EmailService emailService;

  @PostMapping("/send")
  @ResponseStatus(value = HttpStatus.OK)
  public void send(
    @Valid @RequestBody EmailDTO.EmailReqVO emailReqVO
  ) throws MessagingException {
    EmailDTO.EmailResVO emailResVO = emailService.sendVerificationMail(emailReqVO.getEmail());

    if (emailResVO.getEmail().isEmpty()) {
      throw new EmailException.FailedEmailCode();
    }
  }

  @PostMapping("/verify")
  @ResponseStatus(value = HttpStatus.OK)
  public boolean verify(
    @Valid @RequestBody EmailDTO.EmailVerifyReqVO emailVerifyReqVO
  ) {
    boolean result = emailService.verifyCode(
      emailVerifyReqVO.getEmail(),
      emailVerifyReqVO.getCode()
    );

    return result;
  }

}
