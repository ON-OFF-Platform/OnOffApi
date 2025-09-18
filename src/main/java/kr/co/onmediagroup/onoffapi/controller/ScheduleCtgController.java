package kr.co.onmediagroup.onoffapi.controller;

import jakarta.validation.Valid;
import kr.co.onmediagroup.onoffapi.model.dto.Color;
import kr.co.onmediagroup.onoffapi.model.dto.ScheduleCtg;
import kr.co.onmediagroup.onoffapi.model.dto.User;
import kr.co.onmediagroup.onoffapi.model.vo.SchedulesVO;
import kr.co.onmediagroup.onoffapi.service.ScheduleCtgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ctg/schedule")
public class ScheduleCtgController {
  private final ScheduleCtgService scheduleCtgService;

  /**
   * 일정 카테고리 생성
   * @param userPrincipal
   * @param scheduleCtgReqVO
   */
  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public void createCtg(
    @AuthenticationPrincipal User.MinimumUserPrincipal userPrincipal,
    @RequestBody @Valid SchedulesVO.ScheduleCtgReqVO scheduleCtgReqVO
    ) {
    this.scheduleCtgService.createCtg(
      userPrincipal.getUserId(),
      scheduleCtgReqVO.name(),
      scheduleCtgReqVO.sortOrder(),
      scheduleCtgReqVO.colorId()
    );
  }

  /**
   * 일정 카테고리 업데이트
   * @param userPrincipal
   * @param scheduleCtgUpdateVO
   */
  @PostMapping("/update")
  @ResponseStatus(HttpStatus.OK)
  public void updateCtg(
    @AuthenticationPrincipal User.MinimumUserPrincipal userPrincipal,
    @RequestBody @Valid SchedulesVO.ScheduleCtgUpdateVO scheduleCtgUpdateVO
  ) {
    this.scheduleCtgService.updateCtg(
      userPrincipal.getUserId(),
      scheduleCtgUpdateVO.scheduleCtgId(),
      scheduleCtgUpdateVO.name(),
      scheduleCtgUpdateVO.sortOrder(),
      scheduleCtgUpdateVO.colorId()
    );
  }

  /**
   * 일정 카테고리 삭제
   * @param userPrincipal
   * @param scheduleCtgDeleteVO
   */
  @PostMapping("/delete")
  @ResponseStatus(HttpStatus.OK)
  public void deleteCtg(
    @AuthenticationPrincipal User.MinimumUserPrincipal userPrincipal,
    @RequestBody @Valid SchedulesVO.ScheduleCtgDeleteVO scheduleCtgDeleteVO
  ) {
    this.scheduleCtgService.deleteCtg(
      userPrincipal.getUserId(),
      scheduleCtgDeleteVO.scheduleCtgId()
    );
  }

  /**
   * 카테고리 조회
   * @param userPrincipal
   * @return
   */
  @GetMapping("/")
  @ResponseStatus(HttpStatus.OK)
  public List<ScheduleCtg.ScheduleCtgDTO> findAll(
    @AuthenticationPrincipal User.MinimumUserPrincipal userPrincipal
  ) {
    List<ScheduleCtg.ScheduleCtgDTO> scheduleCtgDTOList = this.scheduleCtgService.findAll(userPrincipal.getUserId());

    return scheduleCtgDTOList;
  }


  /**
   * 카테고리 색상 조회
   * @return
   */
  @GetMapping("/color")
  @ResponseStatus(HttpStatus.OK)
  public List<Color.ColorDTO> findColor() {
    List<Color.ColorDTO> colorDTOList = this.scheduleCtgService.findColor();

    return colorDTOList;
  }


}
