package com.lframework.starter.web.inner.controller.system;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.components.security.SecurityConstants;
import com.lframework.starter.web.core.components.security.SecurityUtil;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.inner.bo.system.user.QueryUserRoleBo;
import com.lframework.starter.web.inner.entity.SysRole;
import com.lframework.starter.web.inner.service.system.SysRoleService;
import com.lframework.starter.web.inner.service.system.SysUserRoleService;
import com.lframework.starter.web.inner.vo.system.role.QuerySysRoleVo;
import com.lframework.starter.web.inner.vo.system.user.SysUserRoleSettingVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户授权
 *
 * @author zmj
 */
@Api(tags = "用户授权")
@Validated
@RestController
@RequestMapping("/system/user/role")
public class SysUserRoleController extends DefaultBaseController {

  @Autowired
  private SysUserRoleService sysUserRoleService;

  /**
   * 用户授权
   */
  @ApiOperation("用户授权")
  @HasPermission({"system:user:permission"})
  @PostMapping("/setting")
  public InvokeResult<Void> setting(@Valid @RequestBody SysUserRoleSettingVo vo) {

    sysUserRoleService.setting(vo);

    return InvokeResultBuilder.success();
  }
}
