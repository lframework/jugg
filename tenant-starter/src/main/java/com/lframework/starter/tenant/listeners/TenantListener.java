package com.lframework.starter.tenant.listeners;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.lframework.starter.mybatis.utils.DataSourceUtil;
import com.lframework.starter.web.common.event.ClearTenantEvent;
import com.lframework.starter.web.common.event.ReloadTenantEvent;
import com.lframework.starter.web.common.event.SetTenantEvent;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;

public class TenantListener {

  public static class ReloadTenantListener implements ApplicationListener<ReloadTenantEvent>,
      Ordered {

    @Autowired
    private DynamicDataSourceProperties dynamicDataSourceProperties;

    @Autowired
    private DataSource dataSource;

    @Override
    public void onApplicationEvent(ReloadTenantEvent event) {
      DataSourceProperty dataSourceProperty = dynamicDataSourceProperties.getDatasource()
          .get("master");
      DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
      ds.addDataSource(String.valueOf(event.getTenantId()),
          DataSourceUtil.createDataSource(dataSourceProperty, event.getJdbcUrl(),
              event.getJdbcUsername(), event.getJdbcPassword(), event.getDriver()));
    }

    @Override
    public int getOrder() {
      return Integer.MIN_VALUE;
    }
  }

  public static class SetTenantListener implements ApplicationListener<SetTenantEvent> {

    @Override
    public void onApplicationEvent(SetTenantEvent event) {
      DynamicDataSourceContextHolder.push(String.valueOf(event.getTenantId()));
    }
  }

  public static class ClearTenantListener implements ApplicationListener<ClearTenantEvent> {

    @Override
    public void onApplicationEvent(ClearTenantEvent event) {
      DynamicDataSourceContextHolder.clear();
    }
  }
}
