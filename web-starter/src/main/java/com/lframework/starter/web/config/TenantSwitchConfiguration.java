package com.lframework.starter.web.config;

import com.lframework.starter.web.config.properties.TenantProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties(TenantProperties.class)
public class TenantSwitchConfiguration {

}
