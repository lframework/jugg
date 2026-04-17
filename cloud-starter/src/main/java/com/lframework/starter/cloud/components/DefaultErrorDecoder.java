package com.lframework.starter.cloud.components;

import com.lframework.starter.common.exceptions.BaseException;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.starter.web.core.utils.JsonUtil;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.lang.reflect.Constructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultErrorDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String methodKey, Response response) {
    try {
      Response.Body body = response.body();
      Charset charset = response.charset() == null ? StandardCharsets.UTF_8 : response.charset();
      String resp = body == null ? "" : Util.decodeOrDefault(Util.toByteArray(body.asInputStream()),
          charset, "");
      if (log.isDebugEnabled()) {
        log.debug("开始处理Feign异常请求, methodKey={}, resp={}", methodKey, resp);
      }
      ApiInvokeResult result = JsonUtil.parseObject(resp, ApiInvokeResult.class);
      if (StringUtil.isEmpty(result.getExClass())) {
        return new DefaultClientException(result.getMsg());
      } else {
        return instantiateException(result.getExClass(), result.getMsg());
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

  private Exception instantiateException(String exceptionClassName, String message)
      throws ReflectiveOperationException {
    Class<? extends Exception> exceptionClass = Class.forName(exceptionClassName)
        .asSubclass(Exception.class);
    Constructor<? extends Exception> constructor = exceptionClass.getDeclaredConstructor(
        String.class);
    constructor.setAccessible(true);
    return constructor.newInstance(message);
  }
}
