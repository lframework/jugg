package com.lframework.starter.web.core.utils;

import cn.hutool.core.util.DesensitizedUtil;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.annotations.constants.EncryType;

/**
 * 字段脱敏工具类
 * 提供各种类型字段的脱敏处理功能，支持多种脱敏策略
 * 包括密码、邮箱、姓名、身份证、手机号等敏感信息的脱敏
 *
 * @author lframework@163.com
 */
public class FieldEncryptUtil {

  /**
   * 根据脱敏类型对字符串进行脱敏处理
   * 支持多种预定义的脱敏策略
   *
   * @param str 要脱敏的字符串，可以为null
   * @param type 脱敏类型，不能为null
   * @return 脱敏后的字符串
   */
  public static String encrypt(CharSequence str, EncryType type) {
    String s = str == null ? null : str.toString();
    switch (type) {
      case AUTO: {
        return autoEncrypt(str);
      }
      case PASSWORD: {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
          builder.append(StringPool.ENCRYPT_STR);
        }

        return builder.toString();
      }
      case EMAIL: {
        return DesensitizedUtil.email(s);
      }
      case CHINESE_NAME: {
        return DesensitizedUtil.chineseName(s);
      }
      case ID_CARD: {
        return DesensitizedUtil.idCardNum(s, 4, 4);
      }
      case FIXED_PHONE: {
        return DesensitizedUtil.fixedPhone(s);
      }
      case MOBILE_PHONE: {
        return DesensitizedUtil.mobilePhone(s);
      }
      case CAR_LICENSE: {
        return DesensitizedUtil.carLicense(s);
      }
      case BANK_CARD: {
        return DesensitizedUtil.bankCard(s);
      }
    }

    return StringPool.ENCRYPT_STR;
  }

  /**
   * 自动脱敏处理
   * 根据字符串长度自动选择合适的脱敏策略
   *
   * @param str 要脱敏的字符串，可以为null
   * @return 脱敏后的字符串
   */
  public static String autoEncrypt(CharSequence str) {
    if (StringUtil.isEmpty(str)) {
      return StringPool.ENCRYPT_STR;
    }

    int len = str.length();
    int sub = len >> 1;
    if (sub <= 0) {
      return StringPool.ENCRYPT_STR;
    }

    int sub2 = sub >> 1;
    if (sub2 <= 0) {
      // 字符串的length要么是2 要么是3
      return StringUtil.hide(str, 1, 2);
    }

    return StringUtil.hide(str, sub - sub2, sub - sub2 + sub);
  }
}
