package com.lframework.starter.security.vo.system.config;

import com.lframework.starter.web.components.validation.TypeMismatch;
import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UpdateSysConfigVo implements BaseVo, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 是否允许注册
     */
    @NotNull(message = "请选择是否允许注册！")
    @TypeMismatch(message = "是否允许注册格式错误！")
    private Boolean allowRegist;

    /**
     * 是否允许锁定用户
     */
    @NotNull(message = "请选择是否允许锁定用户！")
    @TypeMismatch(message = "是否允许锁定用户格式错误！")
    private Boolean allowLock;

    /**
     * 登录失败次数
     */
    private Integer failNum;

    /**
     * 是否允许验证码
     */
    @NotNull(message = "请选择是否允许验证码！")
    @TypeMismatch(message = "是否允许验证码格式错误！")
    private Boolean allowCaptcha;
}
