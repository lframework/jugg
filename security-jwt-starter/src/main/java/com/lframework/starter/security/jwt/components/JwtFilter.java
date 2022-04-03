package com.lframework.starter.security.jwt.components;

import com.lframework.common.utils.StringUtil;
import com.lframework.starter.security.components.PermitAllService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Jwt Filter
 *
 * @author zmj
 */
@Slf4j
@Component
@ConditionalOnProperty(value = "default-setting.security.enabled", matchIfMissing = true)
public class JwtFilter extends OncePerRequestFilter {

  @Autowired
  private PermitAllService permitAllService;

  @Autowired
  private UserTokenResolver userTokenResolver;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    if (permitAllService.isMatch(request)) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = userTokenResolver.resolve(request);

    if (!StringUtil.isBlank(token)) {
      // 解析Token
      String userId = userTokenResolver.resolveToken(token);
      if (!StringUtil.isBlank(userId)) {
        filterChain.doFilter(request, response);
        return;
      }
      if (log.isDebugEnabled()) {
        log.debug("Token已过期");
      }
      throw new NonceExpiredException("Token已过期");
    } else {
      if (log.isDebugEnabled()) {
        log.debug("Token无效");
      }
      throw new AccessDeniedException("Token无效");
    }
  }
}
