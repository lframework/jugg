package com.lframework.starter.web.common.event;

import org.springframework.context.ApplicationEvent;

public class ReloadTenantEvent extends ApplicationEvent {

  /**
   * 租户ID
   */
  private Integer tenantId;

  /**
   * JDBC URL
   */
  private String jdbcUrl;

  /**
   * JDBC username
   */
  private String jdbcUsername;

  /**
   * JDBC password
   */
  private String jdbcPassword;

  /**
   * 驱动
   */
  private String driver = "com.mysql.cj.jdbc.Driver";

  public ReloadTenantEvent(Object source, Integer tenantId, String jdbcUrl, String jdbcUsername,
      String jdbcPassword) {
    super(source);
    this.tenantId = tenantId;
    this.jdbcUrl = jdbcUrl;
    this.jdbcUsername = jdbcUsername;
    this.jdbcPassword = jdbcPassword;
  }

  public ReloadTenantEvent(Object source, Integer tenantId, String jdbcUrl, String jdbcUsername,
      String jdbcPassword, String driver) {
    super(source);
    this.tenantId = tenantId;
    this.jdbcUrl = jdbcUrl;
    this.jdbcUsername = jdbcUsername;
    this.jdbcPassword = jdbcPassword;
    this.driver = driver;
  }

  public Integer getTenantId() {
    return tenantId;
  }

  public String getJdbcUrl() {
    return jdbcUrl;
  }

  public String getJdbcUsername() {
    return jdbcUsername;
  }

  public String getJdbcPassword() {
    return jdbcPassword;
  }

  public String getDriver() {
    return driver;
  }
}
