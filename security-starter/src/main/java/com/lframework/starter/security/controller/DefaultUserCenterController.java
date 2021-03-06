package com.lframework.starter.security.controller;

import com.lframework.common.constants.PatternPool;
import com.lframework.common.exceptions.impl.InputErrorException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.RegUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.dto.DefaultOpLogsDto;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.events.UpdateUserEvent;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.IOpLogsService;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.mybatis.vo.QueryOpLogsVo;
import com.lframework.starter.security.bo.oplog.OpLogInUserCenterBo;
import com.lframework.starter.security.bo.usercenter.UserInfoBo;
import com.lframework.starter.web.components.security.PasswordEncoderWrapper;
import com.lframework.starter.web.dto.UserInfoDto;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.web.common.security.AbstractUserDetails;
import com.lframework.web.common.security.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ????????????
 *
 * @author zmj
 */
@Api(tags = "????????????")
@Validated
@RestController
@RequestMapping("/center")
public class DefaultUserCenterController extends DefaultBaseController {

  @Autowired
  private IUserService userService;

  @Autowired
  private IOpLogsService opLogsService;

  @Autowired
  private PasswordEncoderWrapper encoderWrapper;

  /**
   * ??????????????????
   */
  @ApiOperation(value = "??????????????????")
  @GetMapping("/info")
  public InvokeResult<UserInfoBo> getInfo() {

    String userId = getCurrentUser().getId();
    UserInfoDto info = userService.getInfo(userId);

    return InvokeResultBuilder.success(new UserInfoBo(info));
  }

  /**
   * ????????????
   */
  @ApiOperation("????????????")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "?????????", name = "oldPsw", paramType = "query", required = true),
      @ApiImplicitParam(value = "?????????", name = "newPsw", paramType = "query", required = true),
      @ApiImplicitParam(value = "????????????", name = "confirmPsw", paramType = "query", required = true)})
  @OpLog(type = OpLogType.AUTH, name = "???????????????????????????{}???????????????{}", params = {"#oldPsw", "#newPsw"})
  @PatchMapping("/password")
  public InvokeResult<Void> updatePassword(@NotBlank(message = "????????????????????????") String oldPsw,
      @NotBlank(message = "????????????????????????") String newPsw,
      @NotBlank(message = "???????????????????????????") String confirmPsw) {

    AbstractUserDetails user = getCurrentUser();
    if (!encoderWrapper.getEncoder().matches(oldPsw, user.getPassword())) {
      throw new InputErrorException("???????????????????????????????????????");
    }

    if (!StringUtil.equals(newPsw, confirmPsw)) {
      throw new InputErrorException("??????????????????????????????????????????");
    }

    if (!RegUtil.isMatch(PatternPool.PATTERN_PASSWORD, newPsw)) {
      throw new InputErrorException("????????????????????????????????????");
    }

    userService.updatePassword(user.getId(), newPsw);

    //????????????????????????????????????
    SecurityUtil.logout();

    return InvokeResultBuilder.success();
  }

  /**
   * ????????????
   */
  @ApiOperation("????????????")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "???????????????", name = "newEmail", paramType = "query", required = true),
      @ApiImplicitParam(value = "??????????????????", name = "confirmEmail", paramType = "query", required = true)})
  @OpLog(type = OpLogType.AUTH, name = "???????????????????????????{}", params = "#newEmail")
  @PatchMapping("/email")
  public InvokeResult<Void> updateEmail(@NotBlank(message = "??????????????????????????????") String newEmail,
      @NotBlank(message = "?????????????????????????????????") String confirmEmail) {

    AbstractUserDetails user = getCurrentUser();

    if (!StringUtil.equals(newEmail, confirmEmail)) {
      throw new InputErrorException("????????????????????????????????????????????????");
    }

    if (!RegUtil.isMatch(PatternPool.EMAIL, newEmail)) {
      throw new InputErrorException("??????????????????????????????????????????");
    }

    userService.updateEmail(user.getId(), newEmail);

    userService.cleanCacheByKey(user.getId());

    ApplicationUtil.publishEvent(new UpdateUserEvent(this, user.getId()));

    return InvokeResultBuilder.success();
  }

  /**
   * ??????????????????
   */
  @ApiOperation("??????????????????")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "???????????????", name = "newTelephone", paramType = "query", required = true),
      @ApiImplicitParam(value = "??????????????????", name = "confirmTelephone", paramType = "query", required = true)})
  @OpLog(type = OpLogType.AUTH, name = "???????????????????????????????????????{}", params = "#newTelephone")
  @PatchMapping("/telephone")
  public InvokeResult<Void> updateTelephone(@NotBlank(message = "??????????????????????????????") String newTelephone,
      @NotBlank(message = "?????????????????????????????????") String confirmTelephone) {

    AbstractUserDetails user = getCurrentUser();

    if (!StringUtil.equals(newTelephone, confirmTelephone)) {
      throw new InputErrorException("????????????????????????????????????????????????");
    }

    if (!RegUtil.isMatch(PatternPool.PATTERN_CN_TEL, newTelephone)) {
      throw new InputErrorException("??????????????????????????????????????????");
    }

    userService.updateTelephone(user.getId(), newTelephone);

    userService.cleanCacheByKey(user.getId());

    ApplicationUtil.publishEvent(new UpdateUserEvent(this, user.getId()));

    return InvokeResultBuilder.success();
  }

  /**
   * ??????????????????
   */
  @ApiOperation("??????????????????")
  @GetMapping("/oplog")
  public InvokeResult<PageResult<OpLogInUserCenterBo>> oplog(@Valid QueryOpLogsVo vo) {

    vo.setCreateBy(SecurityUtil.getCurrentUser().getId());

    PageResult<DefaultOpLogsDto> pageResult = opLogsService.query(getPageIndex(vo), getPageSize(vo),
        vo);
    List<DefaultOpLogsDto> datas = pageResult.getDatas();
    List<OpLogInUserCenterBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(OpLogInUserCenterBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }
}
