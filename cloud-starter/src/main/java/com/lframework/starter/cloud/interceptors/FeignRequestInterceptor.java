package com.lframework.starter.cloud.interceptors;

import com.lframework.starter.web.utils.RequestUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class FeignRequestInterceptor implements RequestInterceptor {

  @Override
  public void apply(RequestTemplate requestTemplate) {
    HttpServletRequest request = RequestUtil.getRequest();

    if (request != null) {
      String cookie = request.getHeader("Cookie");
      requestTemplate.header("Cookie", cookie);
    }
  }
}
