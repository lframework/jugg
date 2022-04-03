package com.lframework.starter.security.session.components;

import com.lframework.starter.security.components.AbstractAuthenticationSuccessHandler;
import com.lframework.starter.web.components.security.AbstractUserDetails;
import com.lframework.starter.web.dto.LoginDto;
import com.lframework.starter.web.utils.ResponseUtil;
import com.lframework.starter.web.utils.SecurityUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class DefaultAuthenticationSuccessHandler extends AbstractAuthenticationSuccessHandler {

  @Override
  protected void doAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {

    AbstractUserDetails user = SecurityUtil.getCurrentUser();
    LoginDto dto = new LoginDto(request.getSession(false).getId(), user.getName(),
        user.getPermissions());

    ResponseUtil.respSuccessJson(response, dto);
  }
}
