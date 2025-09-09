package kr.co.onmediagroup.onoffapi.controller;

import kr.co.onmediagroup.onoffapi.model.dto.Terms;
import kr.co.onmediagroup.onoffapi.service.TermsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/join/terms")
public class TermsController {
  private final TermsService termsService;

  @GetMapping
  @ResponseStatus(value = HttpStatus.OK)
  public List<Terms.TermsReqDTO> findTermsAll(){
    List<Terms.TermsReqDTO> termsReqDTOList = termsService.findTermsAll();

    return termsReqDTOList;
  }
}
