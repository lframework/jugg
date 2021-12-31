package com.lframework.starter.security.components;

import com.lframework.starter.security.event.LoginEvent;
import com.lframework.starter.web.components.security.AbstractUserDetails;
import com.lframework.starter.web.dto.LoginDto;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.ResponseUtil;
import com.lframework.starter.web.utils.SecurityUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户认证成功处理器
 *
 * @author zmj
 */
@Component
public class DefaultAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        AbstractUserDetails user = SecurityUtil.getCurrentUser();
        LoginDto dto = new LoginDto(request.getSession(false).getId(), user.getName(), user.getPermissions());

        ApplicationUtil.publishEvent(new LoginEvent(this));

        ResponseUtil.respSuccessJson(response, dto);
    }
}
