package com.lframework.starter.web.inner.controller.system;

import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.inner.service.system.SysUserDeptService;
import com.lframework.starter.web.inner.vo.system.dept.SysUserDeptSettingVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户与部门的关系
 *
 * @author zmj
 */
@Api(tags = "用户与部门的关系")
@Validated
@RestController
@RequestMapping("/system/user/dept")
public class SysUserDeptController extends DefaultBaseController {

  @Autowired
  private SysUserDeptService sysUserDeptService;

  /**
   * 设置用户的部门
   */
  @ApiOperation("设置用户的部门")
  @HasPermission({"system:user:modify"})
  @PostMapping("/setting")
  public InvokeResult<Void> setting(@Valid @RequestBody SysUserDeptSettingVo vo) {

    sysUserDeptService.setting(vo);

    return InvokeResultBuilder.success();
  }
}
