package com.lframework.starter.security.bo.auth;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.LoginDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 手机号登录Bo
 *
 * @author zmj
 * @since 2022/4/25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TelephoneLoginBo extends BaseBo<LoginDto> {

    /**
     * 登录信息
     */
    @ApiModelProperty("登录信息")
    private LoginBo loginInfo;

    /**
     * 是否绑定用户
     */
    @ApiModelProperty("是否绑定用户")
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
