package com.lframework.starter.web.config;

import cn.hutool.extra.mail.MailAccount;
import com.lframework.starter.web.impl.MailServiceImpl;
import com.lframework.starter.web.service.IMailService;
import com.sun.mail.util.MailSSLSocketFactory;
import java.security.GeneralSecurityException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MailProperties.class)
public class MailConfiguration {

  @Bean
  @ConditionalOnMissingBean(IMailService.class)
  public IMailService getMailService(MailProperties properties) throws GeneralSecurityException {

    MailAccount account = new MailAccount();
    account.setHost(properties.getHost());
    account.setPort(properties.getPort());
    account.setUser(properties.getUser());
    account.setPass(properties.getPass());
    account.setFrom(properties.getFrom());
    account.setSslEnable(properties.getSslEnable());
    account.setTimeout(properties.getTimeOut());
    account.setConnectionTimeout(properties.getConnectTimeOut());
    if (account.getPort() == null && properties.getSslEnable() != null && properties
        .getSslEnable()) {
      account.setPort(465);
    }

    MailSSLSocketFactory sf = new MailSSLSocketFactory();
    sf.setTrustAllHosts(true);
    account.setCustomProperty("mail.smtp.ssl.socketFactory", sf);

    return new MailServiceImpl(account);
  }
}
