package com.lframework.starter.common.utils;

import cn.hutool.core.util.StrUtil;
import com.lframework.starter.common.constants.PatternPool;

/**
 * 字符串工具类
 *
 * @author zmj
 */
public class StringUtil extends StrUtil {

  /**
   * 邮箱脱敏
   *
   * @param email
   * @return
   */
  public static String encodeEmail(String email) {

    if (!RegUtil.isMatch(PatternPool.PATTERN_EMAIL, email)) {
      return null;
    }

    return email.charAt(0) + "******" + "@" + email.split("@")[1];
  }

  /**
   * 手机号脱敏
   *
   * @param telephone
   * @return
   */
  public static String encodeTelephone(String telephone) {

    if (!RegUtil.isMatch(PatternPool.PATTERN_CN_TEL, telephone)) {
      return null;
    }

    return telephone.substring(0, 3) + "****" + telephone.substring(7);
  }

  /**
   * 字符串匹配，支持* ?通配符
   *
   * @param str
   * @param pattern
   * @return
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
