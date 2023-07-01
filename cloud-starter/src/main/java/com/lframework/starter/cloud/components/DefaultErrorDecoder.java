package com.lframework.starter.cloud.components;

import com.lframework.starter.common.exceptions.BaseException;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.ReflectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.starter.web.utils.JsonUtil;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
public class DefaultErrorDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String methodKey, Response response) {
    try {
      String resp = Util.toString(response.body().asReader());
      if (log.isDebugEnabled()) {
        log.debug("开始处理Feign异常请求, methodKey={}, resp={}", methodKey, resp);
      }
      ApiInvokeResult result = JsonUtil.parseObject(resp, ApiInvokeResult.class);
      if (StringUtil.isEmpty(result.getExClass())) {
        return new DefaultClientException(result.getMsg());
      } else {
        return (Exception) ReflectUtil.newInstance(Class.forName(result.getExClass()),
            result.getMsg());
      }

    } catch (Exception e) {
      if (e instanceof BaseException) {
        if (log.isDebugEnabled()) {
          log.debug(e.getMessage(), e);
        }
        return e;
      } else {
        log.error(e.getMessage(), e);
        return new DefaultSysException(e.getMessage());
      }
    }
  }
}
