package com.lframework.starter.mq.activemq.interceptors;

import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.web.common.tenant.TenantContextHolder;
import com.lframework.starter.web.utils.TenantUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.broker.inteceptor.MessageInterceptor;
import org.apache.activemq.command.Message;

@Slf4j
public class ActiveMQTenantInterceptor implements MessageInterceptor {

  @Override
  public void intercept(ProducerBrokerExchange producerBrokerExchange, Message message) {
    try {
      if (TenantUtil.enableTenant()) {
        Object obj = message.getProperty("tenantId");
        if (obj != null) {
          Integer tenantId = Integer.valueOf(obj.toString());
          TenantContextHolder.setTenantId(tenantId);
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    }
  }
}
