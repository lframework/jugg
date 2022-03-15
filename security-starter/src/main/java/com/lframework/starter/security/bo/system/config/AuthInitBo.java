package com.lframework.starter.security.bo.system.config;

import com.lframework.starter.security.dto.system.config.SysConfigDto;
import com.lframework.starter.web.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthInitBo extends BaseBo<SysConfigDto> {

    /**
     * 是否允许注册
     */
    private Boolean allowRegist;

    /**
     * 是否允许验证码
     */
    private Boolean allowCaptcha;

    public AuthInitBo(SysConfigDto dto) {
        super(dto);
    }
}
