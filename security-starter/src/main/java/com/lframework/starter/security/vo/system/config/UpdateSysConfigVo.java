package com.lframework.starter.security.vo.system.config;

import com.lframework.common.exceptions.impl.InputErrorException;
import com.lframework.common.utils.StringUtil;
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

    /**
     * 是否开启忘记密码
     */
    @NotNull(message = "请选择是否开启忘记密码！")
    @TypeMismatch(message = "是否开启忘记密码格式错误！")
    private Boolean allowForgetPsw;

    /**
     * 忘记密码是否使用邮箱
     */
    @TypeMismatch(message = "忘记密码是否使用邮箱格式错误！")
    private Boolean forgetPswRequireMail;

    /**
     * 忘记密码是否使用短信
     */
    @TypeMismatch(message = "忘记密码是否使用短信格式错误！")
    private Boolean forgetPswRequireSms;

    /**
     * signName
     */
    private String signName;

    /**
     * templateCode
     */
    private String templateCode;

    @Override
    public void validate() {
        if (this.allowForgetPsw) {
            if (this.forgetPswRequireMail == null) {
                throw new InputErrorException("请选择忘记密码是否使用邮箱！");
            }

            if (this.forgetPswRequireSms == null) {
                throw new InputErrorException("请选择忘记密码是否使用短信！");
            }

            if (!this.forgetPswRequireMail && !this.forgetPswRequireSms) {
                throw new InputErrorException("开启忘记密码时，忘记密码使用邮箱、忘记密码使用短信至少开启一个！");
            }

            if (this.forgetPswRequireSms) {
                if (StringUtil.isBlank(this.signName)) {
                    throw new InputErrorException("请输入signName！");
                }

                if (StringUtil.isBlank(this.templateCode)) {
                    throw new InputErrorException("请输入templateCode！");
                }
            }
        }
    }
}
