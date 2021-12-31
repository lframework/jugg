package com.lframework.common.utils;

import cn.hutool.core.util.ReUtil;

import java.util.regex.Pattern;

/**
 * 正则相关工具类
 *
 * @author zmj
 */
public class RegUtil {

    public static boolean isMatch(Pattern pattern, String str) {

        return ReUtil.isMatch(pattern, str);
    }
}
