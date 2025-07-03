package com.lframework.starter.web.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.BasicDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.web.config.properties.SecretProperties;
import com.lframework.starter.web.gen.components.magic.MagicCustomAuthorizationInterceptor;
import com.lframework.starter.web.gen.components.magic.MagicCustomJsonValueProvider;
import com.lframework.starter.web.gen.components.magic.MagicCustomMagicFunction;
import com.lframework.starter.web.gen.components.magic.MagicCustomSqlCache;
import com.lframework.starter.web.inner.entity.Tenant;
import com.lframework.starter.web.inner.service.TenantService;
import com.lframework.starter.web.core.utils.DataSourceUtil;
import com.lframework.starter.web.core.utils.EncryptUtil;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.ssssssss.magicapi.datasource.model.MagicDynamicDataSource;

@Configuration
@Import({
    MagicCustomAuthorizationInterceptor.class,
    MagicCustomJsonValueProvider.class,
    MagicCustomMagicFunction.class,
    MagicCustomSqlCache.class
})
public class MagicCustomAutoConfiguration {

  @Autowired
  private DynamicDataSourceProperties dynamicDataSourceProperties;

  @Bean
  public TaskScheduler taskScheduler() {
    ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
    taskScheduler.setPoolSize(10);
    taskScheduler.initialize();
    return taskScheduler;
  }

  @Bean
  public MagicDynamicDataSource magicDynamicDataSource(DynamicRoutingDataSource dataSource,
      TenantService tenantService, BasicDataSourceCreator basicDataSourceCreator, SecretProperties secretProperties) {
    Map<String, DataSource> dataSourceMap = dataSource.getDataSources();
    MagicDynamicDataSource dynamicDataSource = new MagicDynamicDataSource();
    dynamicDataSource.setDefault(dataSourceMap.get("master"));

    DataSourceProperty dataSourceProperty = dynamicDataSourceProperties.getDatasource()
        .get("master");
    // 这里只加载启用的租户
    Wrapper<Tenant> queryWrapper = Wrappers.lambdaQuery(Tenant.class)
        .eq(Tenant::getAvailable, Boolean.TRUE);
    List<Tenant> tenants = tenantService.list(queryWrapper);

    for (Tenant tenant : tenants) {
      dynamicDataSource.add(String.valueOf(tenant.getId()),
          basicDataSourceCreator.createDataSource(
              DataSourceUtil.createDataSourceProperty(dataSourceProperty, tenant.getJdbcUrl(),
                  tenant.getJdbcUsername(), EncryptUtil.decrypt(tenant.getJdbcPassword(), secretProperties))));
    }

    return dynamicDataSource;
  }
}
