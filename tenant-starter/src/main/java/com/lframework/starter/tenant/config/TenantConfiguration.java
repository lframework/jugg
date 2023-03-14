package com.lframework.starter.tenant.config;

import com.baomidou.dynamic.datasource.provider.AbstractJdbcDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.lframework.starter.common.utils.BeanUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.tenant.interceptors.TenantInterceptorImpl;
import com.lframework.starter.tenant.listeners.TenantListener.ClearTenantListener;
import com.lframework.starter.tenant.listeners.TenantListener.SetTenantListener;
import com.lframework.starter.web.components.tenant.TenantInterceptor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "tenant", value = "enabled", matchIfMissing = false, havingValue = "true")
public class TenantConfiguration {

  @Autowired
  private DynamicDataSourceProperties dynamicDataSourceProperties;

  @Bean
  public TenantInterceptor tenantInterceptor() {
    return new TenantInterceptorImpl();
  }

  @Bean
  public SetTenantListener setTenantListener() {
    return new SetTenantListener();
  }

  @Bean
  public ClearTenantListener clearTenantListener() {
    return new ClearTenantListener();
  }

  @Bean
  public AbstractJdbcDataSourceProvider tenantDataSourceProvider() {
    DataSourceProperty dataSourceProperty = dynamicDataSourceProperties.getDatasource()
        .get("master");
    return new AbstractJdbcDataSourceProvider(dataSourceProperty.getDriverClassName(),
        dataSourceProperty.getUrl(), dataSourceProperty.getUsername(),
        dataSourceProperty.getPassword()) {
      @Override
      protected Map<String, DataSourceProperty> executeStmt(Statement statement)
          throws SQLException {
        Map<String, DataSourceProperty> dataSourcePropertyMap = new HashMap<>();
        String[] tmpArr = dataSourceProperty.getUrl().split("\\?");
        String urlParams = "";
        if (tmpArr.length == 2) {
          urlParams = tmpArr[1];
        }
        // 这里不区分状态
        ResultSet rs = statement.executeQuery("select * from tenant");
        while (rs.next()) {
          String name = rs.getString("id");
          String username = rs.getString("jdbc_username");
          String password = rs.getString("jdbc_password");
          String url = rs.getString("jdbc_url");
          String driver = "com.mysql.cj.jdbc.Driver";
          DataSourceProperty property = new DataSourceProperty();
          BeanUtil.copyProperties(dataSourceProperty, property);
          property.setUsername(username);
          property.setPassword(password);
          if (StringUtil.isNotBlank(urlParams)) {
            if (url.contains("?")) {
              property.setUrl(url + (StringUtil.isNotBlank(urlParams) ? "&" + urlParams : ""));
            } else {
              property.setUrl(url + (StringUtil.isNotBlank(urlParams) ? "?" + urlParams : ""));
            }
          } else {
            property.setUrl(url);
          }
          log.info("加载租户 {} 数据源 url {}", name, property.getUrl());
          property.setDriverClassName(driver);
          dataSourcePropertyMap.put(name, property);
        }
        return dataSourcePropertyMap;
      }
    };
  }
}
