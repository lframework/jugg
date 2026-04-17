package com.lframework.starter.web.inner.bo.auth;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.inner.dto.system.LoginDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 手机号登录Bo
 *
 * @author zmj
 * @since 2022/4/25
 */
@Data
public class TelephoneLoginBo extends BaseBo<LoginDto> {

    /**
     * 登录信息
     */
    @Schema(description = "登录信息")
    private LoginBo loginInfo;

    /**
     * 是否绑定用户
     */
    @Schema(description = "是否绑定用户")
    private Boolean isBind = Boolean.TRUE;

    public TelephoneLoginBo() {

    }

    public TelephoneLoginBo(LoginDto dto) {

        this.afterInit(dto);
    }

    @Override
    protected void afterInit(LoginDto dto) {

        this.loginInfo = new LoginBo(dto);
    }
}
