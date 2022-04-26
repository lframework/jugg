package com.lframework.starter.security.controller.system;

import com.lframework.starter.mybatis.entity.SysConfig;
import com.lframework.starter.mybatis.service.system.ISysConfigService;
import com.lframework.starter.mybatis.vo.system.config.UpdateSysConfigVo;
import com.lframework.starter.security.bo.system.config.GetSysConfigBo;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "系统设置")
@Validated
@RestController
@RequestMapping("/system/config")
public class SysConfigController extends DefaultBaseController {

  @Autowired
  private ISysConfigService sysConfigService;

  @ApiOperation("查询系统设置")
  @PreAuthorize("@permission.valid('system:config:modify')")
  @GetMapping
  public InvokeResult<GetSysConfigBo> get() {

    SysConfig data = sysConfigService.get();

    return InvokeResultBuilder.success(new GetSysConfigBo(data));
  }

  @ApiOperation("修改系统设置")
  @PreAuthorize("@permission.valid('system:config:modify')")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSysConfigVo vo) {

    vo.validate();

    sysConfigService.update(vo);

    return InvokeResultBuilder.success();
  }
}
