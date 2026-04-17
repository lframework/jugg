package com.lframework.starter.web.config;

import com.baomidou.dynamic.datasource.processor.DsJakartaHeaderProcessor;
import com.baomidou.dynamic.datasource.processor.DsJakartaSessionProcessor;
import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.baomidou.dynamic.datasource.processor.DsSpelExpressionProcessor;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.lframework.starter.web.core.components.tenant.TenantContextHolder;
import com.lframework.starter.web.core.constants.MyBatisStringPool;
import com.lframework.starter.web.core.utils.TenantUtil;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(value = DynamicDataSourceAutoConfiguration.class)
public class TenantDynamicDataSourceAutoConfiguration {

  public class DsCurTenantProcessor extends DsProcessor {

    private String primary;

    public DsCurTenantProcessor(String primary) {
      this.primary = primary;
    }

    @Override
    public boolean matches(String key) {
      return key.equals(MyBatisStringPool.DS_KEY_CUR_TENANT);
    }

    @Override
    public String doDetermineDatasource(MethodInvocation invocation, String key) {

      if (TenantUtil.enableTenant()) {
        return TenantContextHolder.getTenantIdStr();
      }
      return this.primary;
    }
  }

  @Bean
  public DsProcessor dsProcessor(DynamicDataSourceProperties properties) {
    DsProcessor headerProcessor = new DsJakartaHeaderProcessor();
    DsProcessor sessionProcessor = new DsJakartaSessionProcessor();
    DsCurTenantProcessor curTenantProcessor = new DsCurTenantProcessor(properties.getPrimary());
    DsSpelExpressionProcessor spelExpressionProcessor = new DsSpelExpressionProcessor();
    headerProcessor.setNextProcessor(sessionProcessor);
    sessionProcessor.setNextProcessor(spelExpressionProcessor);
    spelExpressionProcessor.setNextProcessor(curTenantProcessor);
    return headerProcessor;
  }
}
