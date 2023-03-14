package com.lframework.starter.web.utils;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.annotations.constants.EncryType;

public class FieldEncryptUtil {

  public static String encrypt(CharSequence str, EncryType type) {
    switch (type) {
      case AUTO: {
        return autoEncrypt(str);
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
