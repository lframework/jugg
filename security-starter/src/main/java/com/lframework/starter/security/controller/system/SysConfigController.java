package com.lframework.starter.security.controller.system;

import com.lframework.starter.security.bo.system.config.GetSysConfigBo;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.security.dto.system.config.SysConfigDto;
import com.lframework.starter.security.service.system.ISysConfigService;
import com.lframework.starter.security.vo.system.config.UpdateSysConfigVo;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/system/config")
@ConditionalOnProperty(value = "default-setting.sys-function.enabled", matchIfMissing = true)
public class SysConfigController extends DefaultBaseController {

    @Autowired
    private ISysConfigService sysConfigService;

    @PreAuthorize("@permission.valid('system:config:modify')")
    @GetMapping
    public InvokeResult get() {

        SysConfigDto data = sysConfigService.get();

        return InvokeResultBuilder.success(new GetSysConfigBo(data));
    }

    @PreAuthorize("@permission.valid('system:config:modify')")
    @PutMapping
    public InvokeResult update(@Valid UpdateSysConfigVo vo) {
        sysConfigService.update(vo);

        return InvokeResultBuilder.success();
    }
}
