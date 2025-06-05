package com.lframework.starter.web.core.utils;

import cn.hutool.core.util.DesensitizedUtil;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.annotations.constants.EncryType;

public class FieldEncryptUtil {

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
