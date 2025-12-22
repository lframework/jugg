package com.lframework.starter.web.core.utils;

import com.baomidou.dynamic.datasource.creator.BasicDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.lframework.starter.common.utils.BeanUtil;
import com.lframework.starter.common.utils.StringUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

/**
 * 数据源工具类 提供动态数据源创建和配置功能，支持多数据源管理 包括数据源属性创建、数据源实例创建等功能
 *
 * @author lframework@163.com
 */
@Slf4j
public class DataSourceUtil {

  /**
   * 默认数据源驱动类 MySQL 8.0+ 的JDBC驱动类
   */
  public static final String DEFAULT_DATASOURCE_DRIVER = "com.mysql.cj.jdbc.Driver";

  /**
   * 创建数据源属性（使用默认驱动） 基于现有数据源属性创建新的数据源属性
   *
   * @param sourceProperty 源数据源属性，不能为null
   * @param url            数据库URL，不能为null
   * @param username       用户名，不能为null
   * @param password       密码，不能为null
   * @return 新的数据源属性
   */
  public static DataSourceProperty createDataSourceProperty(DataSourceProperty sourceProperty,
      String url,
      String username,
      String password) {
    return createDataSourceProperty(sourceProperty, url, username, password,
        DEFAULT_DATASOURCE_DRIVER);
  }

  /**
   * 创建数据源属性（指定驱动） 基于现有数据源属性创建新的数据源属性
   *
   * @param sourceProperty 源数据源属性，不能为null
   * @param url            数据库URL，不能为null
   * @param username       用户名，不能为null
   * @param password       密码，不能为null
   * @param driver         驱动类名，不能为null
   * @return 新的数据源属性
   */
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

  /**
   * 创建数据源（使用默认驱动） 基于数据源属性创建数据源实例
   *
   * @param sourceProperty 源数据源属性，不能为null
   * @param url            数据库URL，不能为null
   * @param username       用户名，不能为null
   * @param password       密码，不能为null
   * @return 数据源实例
   */
  public static DataSource createDataSource(DataSourceProperty sourceProperty, String url,
      String username,
      String password) {

    return createDataSource(sourceProperty, url, username, password, DEFAULT_DATASOURCE_DRIVER);
  }

  /**
   * 创建数据源（指定驱动） 基于数据源属性创建数据源实例
   *
   * @param sourceProperty 源数据源属性，不能为null
   * @param url            数据库URL，不能为null
   * @param username       用户名，不能为null
   * @param password       密码，不能为null
   * @param driver         驱动类名，不能为null
   * @return 数据源实例
   */
  public static DataSource createDataSource(DataSourceProperty sourceProperty, String url,
      String username,
      String password, String driver) {

    BasicDataSourceCreator basicDataSourceCreator = ApplicationUtil.getBean(
        BasicDataSourceCreator.class);

    return basicDataSourceCreator.createDataSource(
        createDataSourceProperty(sourceProperty, url, username, password, driver));
  }

  /**
   * 验证数据源连接
   *
   * @return
   */
  public static boolean validConnection(String url, String username, String password) {
    Connection conn = null;
    try {
      conn = DriverManager.getConnection(url, username, password);
      return true;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    } finally {
      JdbcUtils.closeConnection(conn);
    }
    return false;
  }
}
