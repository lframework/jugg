package com.lframework.starter.mq.rabbitmq.config;

import com.lframework.starter.common.utils.ArrayUtil;
import com.lframework.starter.web.core.components.tenant.TenantContextHolder;
import com.lframework.starter.web.core.utils.TenantUtil;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.amqp.core.Message;

@Slf4j
public class TenantMethodInterceptor implements MethodInterceptor {

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    boolean isTenantEnabled = TenantUtil.enableTenant();
    Integer tenantId = null;

    if (isTenantEnabled) {
      log.debug("RabbitMq开始处理TenantId");
      tenantId = convertTenantId(invocation);
      log.debug("TenantId = {}", tenantId);
      if (tenantId != null) {
        TenantContextHolder.setTenantId(tenantId);
      }
    }

    try {
      return invocation.proceed();
    } catch (Throwable e) {
      log.error(e.getMessage(), e);
      throw e;
    } finally {
      TenantContextHolder.clearTenantId();
    }
  }

  private Integer convertTenantId(MethodInvocation invocation) {
    Object[] args = invocation.getArguments();

    if (ArrayUtil.isEmpty(args)) {
      return null;
    }

    for (Object arg : args) {
      if (arg instanceof Message) {
        Message message = (Message) arg;
        Object headerValue = message.getMessageProperties().getHeader("tenantId");
        if (headerValue instanceof Integer) {
          return (Integer) headerValue;
        } else if (headerValue != null) {
          try {
            return Integer.parseInt(headerValue.toString());
          } catch (NumberFormatException e) {
            return null;
          }
        }
      }
    }

    return null;
  }
}
