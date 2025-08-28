package kr.co.onmediagroup.onoffapi.service;

import kr.co.onmediagroup.onoffapi.exception.TermsException;
import kr.co.onmediagroup.onoffapi.model.dto.Terms;
import kr.co.onmediagroup.onoffapi.model.entity.TermsEntity;
import kr.co.onmediagroup.onoffapi.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static kr.co.onmediagroup.onoffapi.model.ModelConverter.MODEL_MAPPER;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TermsService {
  private final TermsRepository termsRepository;

  public List<Terms.TermsReqDTO> findTermsAll() {
    List<TermsEntity> termsEntityList = termsRepository.findAll();

    if (termsEntityList.isEmpty()) {
      throw new TermsException.NoTerms();
    }

    List<Terms.TermsReqDTO> termsReqDTOList = termsEntityList.stream()
      .map(entity -> MODEL_MAPPER.map(entity, Terms.TermsReqDTO.class))
      .collect(Collectors.toList());

    return termsReqDTOList;
  }
}
