package com.lframework.starter.web.config;

import com.aliyun.dysmsapi20170525.Client;
import com.lframework.starter.common.utils.AliSmsUtil;
import com.lframework.starter.web.impl.AliSmsServiceImpl;
import com.lframework.starter.web.service.AliSmsService;
import com.lframework.starter.web.service.SysParameterService;
import com.lframework.starter.web.utils.JsonUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AliSmsConfiguration {

  @Bean
  @Scope("prototype")
  @ConditionalOnMissingBean(AliSmsService.class)
  public AliSmsService getAliSmsService(SysParameterService sysParameterService) throws Exception {

    String smsConfig = sysParameterService.findRequiredByKey("sms.ali");
    AliSmsProperties config = JsonUtil.parseObject(smsConfig, AliSmsProperties.class);

    Client client = AliSmsUtil.createClient(config.getAccessKeyId(), config.getAccessKeySecret(),
        config.getEndpoint());
    return new AliSmsServiceImpl(client);
  }
}
