package com.lframework.starter.web.core.utils;

import com.baomidou.dynamic.datasource.creator.BasicDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.lframework.starter.common.utils.BeanUtil;
import com.lframework.starter.common.utils.StringUtil;
import javax.sql.DataSource;

public class DataSourceUtil {

  public static final String DEFAULT_DATASOURCE_DRIVER = "com.mysql.cj.jdbc.Driver";

  public static DataSourceProperty createDataSourceProperty(DataSourceProperty sourceProperty,
      String url,
      String username,
      String password) {
    return createDataSourceProperty(sourceProperty, url, username, password,
        DEFAULT_DATASOURCE_DRIVER);
  }

  public static DataSourceProperty createDataSourceProperty(DataSourceProperty sourceProperty,
      String url,
      String username,
      String password, String driver) {
    String[] tmpArr = sourceProperty.getUrl().split("\\?");
    String urlParams = "";
    if (tmpArr.length == 2) {
      urlParams = tmpArr[1];
    }

    DataSourceProperty property = new DataSourceProperty();
    BeanUtil.copyProperties(sourceProperty, property, "poolName", "type", "driverClassName",
        "url", "password", "jndiName", "publicKey");
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
    property.setDriverClassName(driver);

    return property;
  }

  public static DataSource createDataSource(DataSourceProperty sourceProperty, String url,
      String username,
      String password) {

    return createDataSource(sourceProperty, url, username, password, DEFAULT_DATASOURCE_DRIVER);
  }

  public static DataSource createDataSource(DataSourceProperty sourceProperty, String url,
      String username,
      String password, String driver) {

    BasicDataSourceCreator basicDataSourceCreator = ApplicationUtil.getBean(
        BasicDataSourceCreator.class);

    return basicDataSourceCreator.createDataSource(
        createDataSourceProperty(sourceProperty, url, username, password, driver));
  }
}
