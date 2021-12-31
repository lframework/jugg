package com.lframework.starter.security.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 登陆密码Encoder Wrapper
 * 用于包装Encoder 方便统一管理
 *
 * @author zmj
 */
@Component
public class PasswordEncoderWrapper {

    @Autowired
    private PasswordEncoder encoder;

    public PasswordEncoder getEncoder() {

        return encoder;
    }
}
