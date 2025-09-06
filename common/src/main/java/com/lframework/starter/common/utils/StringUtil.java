package com.lframework.starter.common.utils;

import cn.hutool.core.util.StrUtil;
import com.lframework.starter.common.constants.PatternPool;

/**
 * 字符串工具类
 * 基于HuTool的StrUtil进行扩展，提供字符串操作相关的工具方法
 * 包括字符串验证、脱敏处理、模式匹配等功能
 *
 * @author lframework@163.com
 */
public class StringUtil extends StrUtil {

  /**
   * 邮箱地址脱敏处理
   * 将邮箱地址进行脱敏，保留首字符和域名部分
   *
   * @param email 邮箱地址，不能为null
   * @return 脱敏后的邮箱地址，如果格式不正确则返回null
   */
  public static String encodeEmail(String email) {

    if (!RegUtil.isMatch(PatternPool.PATTERN_EMAIL, email)) {
      return null;
    }

    return email.charAt(0) + "******" + "@" + email.split("@")[1];
  }

  /**
   * 手机号码脱敏处理
   * 将手机号码进行脱敏，保留前3位和后4位
   *
   * @param telephone 手机号码，不能为null
   * @return 脱敏后的手机号码，如果格式不正确则返回null
   */
  public static String encodeTelephone(String telephone) {

    if (!RegUtil.isMatch(PatternPool.PATTERN_CN_TEL, telephone)) {
      return null;
    }

    return telephone.substring(0, 3) + "****" + telephone.substring(7);
  }

  /**
   * 字符串模式匹配，支持*和?通配符
   * 支持*匹配任意字符，?匹配单个字符的通配符匹配
   *
   * @param str 要匹配的字符串，不能为null
   * @param pattern 匹配模式，支持*和?通配符，不能为null
   * @return true-匹配成功，false-匹配失败
   */
  public static boolean strMatch(String str, String pattern) {
    if (StringUtil.isEmpty(str) && StringUtil.isEmpty(pattern)) {
      return true;
    }
    if ("*".equals(str)) {
      return true;
    }
    if (StringUtil.isEmpty(str) || StringUtil.isEmpty(pattern)) {
      return false;
    }
    if ("?".equals(str.substring(0, 1))) {
      return strMatch(str.substring(1), pattern.substring(1));
    } else if ("*".equals(str.substring(0, 1))) {
      return strMatch(str.substring(1), pattern) || strMatch(str.substring(1), pattern.substring(1))
          || strMatch(str, pattern.substring(1));
    } else if (pattern.substring(0, 1).equals(str.substring(0, 1))) {
      return strMatch(str.substring(1), pattern.substring(1));
    } else {
      return false;
    }
  }
}
