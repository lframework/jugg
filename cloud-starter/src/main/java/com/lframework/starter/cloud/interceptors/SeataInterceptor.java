package com.lframework.starter.cloud.interceptors;

import com.lframework.common.utils.StringUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;
import org.springframework.stereotype.Component;

@Component
public class SeataInterceptor implements RequestInterceptor {

  @Override
  public void apply(RequestTemplate template) {

    String currentXid = RootContext.getXID();
    if (!StringUtil.isEmpty(currentXid)) {
      template.header(RootContext.KEY_XID, currentXid);
    }
  }
}
