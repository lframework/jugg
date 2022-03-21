package com.lframework.common.utils;

import cn.hutool.core.util.StrUtil;
import com.lframework.common.constants.PatternPool;

/**
 * 字符串工具类
 *
 * @author zmj
 */
public class StringUtil extends StrUtil {

    /**
     * 邮箱脱敏
     * @param email
     * @return
     */
    public static String encodeEmail(String email) {

        if (!RegUtil.isMatch(PatternPool.PATTERN_EMAIL, email)) {
            return null;
        }

        return email.substring(0, 1) + "******" + email.split("@")[1];
    }

    /**
     * 手机号脱敏
     * @param telephone
     * @return
     */
    public static String encodeTelephone(String telephone) {

        if (!RegUtil.isMatch(PatternPool.PATTERN_CN_TEL, telephone)) {
            return null;
        }

        return telephone.substring(0, 3) + "******" + telephone.substring(9);
    }
}
