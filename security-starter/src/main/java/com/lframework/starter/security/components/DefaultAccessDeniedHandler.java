package com.lframework.starter.security.components;

import com.lframework.starter.web.utils.ResponseUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * 用户无权限处理器
 *
 * @author zmj
 */
@Component
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {

    ResponseUtil
        .respFailJson(response, new com.lframework.common.exceptions.impl.AccessDeniedException());
  }
}
