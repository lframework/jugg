package com.lframework.starter.security.dto.system.config;

import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;
import java.io.Serializable;

/**
 * <p>
 * 系统设置 Dto
 * </p>
 *
 * @author zmj
 */
@Data
public class SysConfigDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    public static final String CACHE_NAME = "SysConfigDto";

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

    /**
     * 是否开启忘记密码
     */
    private Boolean allowForgetPsw;

    /**
     * 忘记密码是否使用邮箱
     */
    private Boolean forgetPswRequireMail;

    /**
     * 忘记密码是否使用短信
     */
    private Boolean forgetPswRequireSms;

    /**
     * signName
     */
    private String signName;

    /**
     * templateCode
     */
    private String templateCode;

}
