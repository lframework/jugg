package com.lframework.starter.web.config;

import cn.hutool.extra.mail.MailAccount;
import com.lframework.starter.web.config.properties.MailProperties;
import com.lframework.starter.web.impl.MailServiceImpl;
import com.lframework.starter.web.service.MailService;
import com.lframework.starter.web.service.SysConfService;
import com.lframework.starter.web.utils.JsonUtil;
import com.sun.mail.util.MailSSLSocketFactory;
import java.security.GeneralSecurityException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MailAutoConfiguration {

  @Bean
  @Scope("prototype")
  @ConditionalOnMissingBean(MailService.class)
  public MailService getMailService(SysConfService sysConfService)
      throws GeneralSecurityException {

    String configStr = sysConfService.findRequiredByKey("mail");
    MailProperties properties = JsonUtil.parseObject(configStr, MailProperties.class);
    MailAccount account = new MailAccount();
    account.setHost(properties.getHost());
    account.setPort(properties.getPort());
    account.setUser(properties.getUser());
    account.setPass(properties.getPass());
    account.setFrom(properties.getFrom());
    account.setSslEnable(properties.getSslEnable());
    account.setTimeout(properties.getTimeOut());
    account.setConnectionTimeout(properties.getConnectTimeOut());

    MailSSLSocketFactory sf = new MailSSLSocketFactory();
    sf.setTrustAllHosts(true);
    account.setCustomProperty("mail.smtp.ssl.socketFactory", sf);

    return new MailServiceImpl(account);
  }
}
