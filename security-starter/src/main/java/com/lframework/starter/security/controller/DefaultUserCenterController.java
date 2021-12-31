package com.lframework.starter.security.controller;

import com.lframework.common.constants.PatternPool;
import com.lframework.common.exceptions.impl.InputErrorException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.RegUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.dto.DefaultOpLogsDto;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.IOpLogsService;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.mybatis.vo.QueryOpLogsVo;
import com.lframework.starter.security.bo.oplog.OpLogInUserCenterBo;
import com.lframework.starter.security.bo.usercenter.UserInfoBo;
import com.lframework.starter.security.components.PasswordEncoderWrapper;
import com.lframework.starter.web.components.security.AbstractUserDetails;
import com.lframework.starter.web.dto.UserInfoDto;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户中心
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/center")
@ConditionalOnProperty(value = "default-setting.controller.enabled", matchIfMissing = true)
public class DefaultUserCenterController extends DefaultBaseController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IOpLogsService opLogsService;

    @Autowired
    private PasswordEncoderWrapper encoderWrapper;

    /**
     * 获取用户信息
     */
    @GetMapping("/info")
    public InvokeResult getInfo() {

        String userId = getCurrentUser().getId();
        UserInfoDto info = userService.getInfo(userId);

        return InvokeResultBuilder.success(new UserInfoBo(info));
    }

    /**
     * 修改密码
     */
    @OpLog(type = OpLogType.AUTH, name = "修改密码，原密码：{}，新密码：{}", params = {"#oldPsw", "#newPsw"})
    @PatchMapping("/password")
    public InvokeResult updatePassword(@NotBlank(message = "旧密码不能为空！") String oldPsw,
            @NotBlank(message = "新密码不能为空！") String newPsw, @NotBlank(message = "确认密码不能为空！") String confirmPsw) {

        AbstractUserDetails user = getCurrentUser();
        if (!encoderWrapper.getEncoder().matches(oldPsw, user.getPassword())) {
            throw new InputErrorException("旧密码不正确，请重新输入！");
        }

        if (!StringUtil.equals(newPsw, confirmPsw)) {
            throw new InputErrorException("两次密码输入不一致，请检查！");
        }

        if (!RegUtil.isMatch(PatternPool.PATTERN_PASSWORD, newPsw)) {
            throw new InputErrorException("密码格式不正确，请检查！");
        }

        userService.updatePassword(user.getId(), newPsw);

        //修改密码后，退出登录状态
        SecurityUtil.logout();

        return InvokeResultBuilder.success();
    }

    /**
     * 修改邮箱
     */
    @OpLog(type = OpLogType.AUTH, name = "修改邮箱，新邮箱：{}", params = "#newEmail")
    @PatchMapping("/email")
    public InvokeResult updateEmail(@NotBlank(message = "新邮箱地址不能为空！") String newEmail,
            @NotBlank(message = "确认邮箱地址不能为空！") String confirmEmail) {

        AbstractUserDetails user = getCurrentUser();

        if (!StringUtil.equals(newEmail, confirmEmail)) {
            throw new InputErrorException("两次邮箱地址输入不一致，请检查！");
        }

        if (!RegUtil.isMatch(PatternPool.EMAIL, newEmail)) {
            throw new InputErrorException("邮箱地址格式不正确，请检查！");
        }

        userService.updateEmail(user.getId(), newEmail);

        return InvokeResultBuilder.success();
    }

    /**
     * 修改联系电话
     */
    @OpLog(type = OpLogType.AUTH, name = "修改联系电话，新联系电话：{}", params = "#newTelephone")
    @PatchMapping("/telephone")
    public InvokeResult updateTelephone(@NotBlank(message = "新联系电话不能为空！") String newTelephone,
            @NotBlank(message = "确认联系电话不能为空！") String confirmTelephone) {

        AbstractUserDetails user = getCurrentUser();

        if (!StringUtil.equals(newTelephone, confirmTelephone)) {
            throw new InputErrorException("两次联系电话输入不一致，请检查！");
        }

        if (!RegUtil.isMatch(PatternPool.PATTERN_CN_TEL, newTelephone)) {
            throw new InputErrorException("联系电话格式不正确，请检查！");
        }

        userService.updateTelephone(user.getId(), newTelephone);

        return InvokeResultBuilder.success();
    }

    /**
     * 查询操作日志
     */
    @GetMapping("/oplog")
    public InvokeResult oplog(@Valid QueryOpLogsVo vo) {

        vo.setCreateBy(SecurityUtil.getCurrentUser().getId());

        PageResult<DefaultOpLogsDto> pageResult = opLogsService.query(getPageIndex(vo), getPageSize(vo), vo);
        List<DefaultOpLogsDto> datas = pageResult.getDatas();
        if (!CollectionUtil.isEmpty(datas)) {
            List<OpLogInUserCenterBo> results = datas.stream().map(OpLogInUserCenterBo::new)
                    .collect(Collectors.toList());

            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }
}
