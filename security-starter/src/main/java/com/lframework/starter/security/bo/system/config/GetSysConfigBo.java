package com.lframework.starter.security.bo.system.config;

import com.lframework.starter.security.dto.system.config.SysConfigDto;
import com.lframework.starter.web.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetSysConfigBo extends BaseBo<SysConfigDto> {

    /**
     * ID
     */
    private String id;

    /**
     * 是否允许注册
     */
    private Boolean allowRegist;

    /**
     * 是否允许锁定用户
     */
    private Boolean allowLock;

    /**
     * 登录失败次数
     */
    private Integer failNum;

    /**
     * 是否允许验证码
     */
    private Boolean allowCaptcha;

    public GetSysConfigBo(SysConfigDto dto) {
        super(dto);
    }
}
