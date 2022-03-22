package com.lframework.common.constants;

/**
 * 常量池
 *
 * @author zmj
 */
public interface StringPool {

  /**
   * 空格
   */
  String SPACE = " ";

  /**
   * 空字符串
   */
  String EMPTY_STR = "";

  /**
   * 字符串分隔符
   */
  String STR_SPLIT = ",";

  /**
   * 中文字符串分隔符
   */
  String STR_SPLIT_CN = "，";

  /**
   * 城市名称分隔符
   */
  String CITY_SPLIT = "/";

  /**
   * utf-8字符集
   */
  String CHARACTER_ENCODING_UTF_8 = "utf-8";

  /**
   * 日期格式
   */
  String DATE_PATTERN = "yyyy-MM-dd";

  /**
   * 时间格式
   */
  String TIME_PATTERN = "HH:mm:ss";

  /**
   * 日期时间格式
   */
  String DATE_TIME_PATTERN = DATE_PATTERN + SPACE + TIME_PATTERN;

  /**
   * 年月日时
   */
  String DATE_TIME_HOUR_PATTER = DATE_PATTERN + SPACE + "HH";

  /**
   * 小数点
   */
  String DECIMAL_POINT = ".";

  /**
   * 零
   */
  String ZERO = "0";

  /**
   * 登录验证码在redis中的key值
   */
  String LOGIN_CAPTCHA_KEY = "login_captcha_key_{}";

  /**
   * 登录API URL
   */
  String LOGIN_API_URL = "/auth/login";

  /**
   * 退出登录API URL
   */
  String LOGOUT_API_URL = "/auth/logout";

  /**
   * 获取验证码URL
   */
  String CAPTCHA_URL = "/auth/captcha";

  /**
   * 登录初始化参数URL
   */
  String AUTH_INIT_URL = "/auth/init";

  /**
   * 注册URL
   */
  String AUTH_REGIST_URL = "/auth/regist";

  /**
   * 登录提交验证码的参数名
   */
  String CAPTCHA_PARAMETER_NAME = "captcha";

  /**
   * 登录提交SN的参数名
   */
  String SN_PARAMETER_NAME = "sn";

  /**
   * SessionId在Header中的key值
   */
  String HEADER_NAME_SESSION_ID = "X-Auth-Token";

  /**
   * 管理员权限名称
   */
  String PERMISSION_ADMIN_NAME = "admin";

  /**
   * 请求ID再Header中的key值
   */
  String HEADER_NAME_REQUEST_ID = "Request-Id";
}
