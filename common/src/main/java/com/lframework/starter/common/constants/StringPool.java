package com.lframework.starter.common.constants;

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
   * 单元格占位符
   */
  String CELL_PLACEHOLDER = "-";

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
   * Excel中的日期格式
   */
  String EXCEL_DATE_PATTERN = "yyyy/MM/dd";

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
   * 请求ID再Header中的key值
   */
  String HEADER_NAME_REQUEST_ID = "Request-Id";

  /**
   * 数据字典分隔符
   */
  String DATA_DIC_SPLIT = "@";

  /**
   * 加密字符
   */
  String ENCRYPT_STR = "*";

  /**
   * 租户ID在Qrtz中的Key
   */
  String TENANT_ID_QRTZ = "__tenantId";
}
