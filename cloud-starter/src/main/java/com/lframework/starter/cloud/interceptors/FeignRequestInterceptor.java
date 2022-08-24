package com.lframework.starter.cloud.interceptors;

import com.lframework.starter.web.utils.RequestUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FeignRequestInterceptor implements RequestInterceptor {

  @Override
  public void apply(RequestTemplate requestTemplate) {
    try {
      HttpServletRequest request = RequestUtil.getRequest();

      Enumeration<String> headerNames = request.getHeaderNames();
      while (headerNames.hasMoreElements()) {
        String headerName = headerNames.nextElement();
        requestTemplate.header(headerName, request.getHeader(headerName));
      }
    } catch (Exception e) {
      log.warn(e.getMessage(), e);
    }
  }
}
