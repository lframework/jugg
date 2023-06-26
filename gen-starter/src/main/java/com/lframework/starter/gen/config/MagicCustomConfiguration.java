package com.lframework.starter.gen.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.BasicDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.lframework.starter.mybatis.entity.Tenant;
import com.lframework.starter.mybatis.service.TenantService;
import com.lframework.starter.mybatis.utils.DataSourceUtil;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.ssssssss.magicapi.datasource.model.MagicDynamicDataSource;

@Configuration
public class MagicCustomConfiguration {

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
      TenantService tenantService, BasicDataSourceCreator basicDataSourceCreator) {
    Map<String, DataSource> dataSourceMap = dataSource.getDataSources();
    MagicDynamicDataSource dynamicDataSource = new MagicDynamicDataSource();
    dynamicDataSource.setDefault(dataSourceMap.get("master"));

    DataSourceProperty dataSourceProperty = dynamicDataSourceProperties.getDatasource()
        .get("master");

    List<Tenant> tenants = tenantService.list();

    for (Tenant tenant : tenants) {
      dynamicDataSource.add(String.valueOf(tenant.getId()),
          basicDataSourceCreator.createDataSource(
              DataSourceUtil.createDataSourceProperty(dataSourceProperty, tenant.getJdbcUrl(),
                  tenant.getJdbcUsername(), tenant.getJdbcPassword())));
    }

    return dynamicDataSource;
  }
}
