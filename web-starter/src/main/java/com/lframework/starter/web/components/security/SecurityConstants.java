package com.lframework.starter.web.components.security;

public interface SecurityConstants {

  /**
   * 管理员权限名称
   */
  String PERMISSION_ADMIN_NAME = "admin";

  /**
   * Token起始字符串
   */
  String TOKEN_START_WITH_STR = "Bearer-";

  /**
   * 用户Token在redis中的key
   */
  String USER_TOKEN_KEY = "user_token_key_{}";

  /**
   * 用户信息在redis中的key
   */
  String USER_INFO_KEY = "user_info_key";

  /**
   * 数据权限SQL在redis中的key
   */
  String DATA_PERMISSION_SQL_MAP = "__data_permission_sql_map";

  /**
   * 数据权限SQL变量在redis中的key
   */
  String DATA_PERMISSION_SQL_VAR = "__data_permission_sql_var";
}
