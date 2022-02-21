package com.lframework.common.constants;

import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 *
 * @author zmj
 */
public class PatternPool extends cn.hutool.core.lang.PatternPool {

    /**
     * 不包含+86的手机号码
     */
    public static final String PATTERN_STR_CN_TEL = "^1[3-9]\\d{9}$";

    public static final Pattern PATTERN_CN_TEL = Pattern.compile(PATTERN_STR_CN_TEL);

    /**
     * 电子邮箱
     */
    public static final String PATTERN_STR_EMAIL = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

    public static final Pattern PATTERN_EMAIL = Pattern.compile(PATTERN_STR_EMAIL);

    /**
     * 5-16位密码
     * 只允许包含大写字母、小写字母、数字、下划线
     */
    public static final String PATTERN_STR_PASSWORD = "^[a-zA-Z0-9_]{5,16}$";

    public static final Pattern PATTERN_PASSWORD = Pattern.compile(PATTERN_STR_PASSWORD);

    /**
     * 是否 整数
     */
    public static final String PATTERN_STR_IS_INTEGER = "^(-?[1-9]\\d*|[0])$";

    public static final Pattern PATTERN_IS_INTEGER = Pattern.compile(PATTERN_STR_IS_INTEGER);

    /**
     * 是否 正整数
     */
    public static final String PATTERN_STR_IS_INTEGER_GT_ZERO = "^[1-9]\\d*$";

    public static final Pattern PATTERN_IS_INTEGER_GT_ZERO = Pattern.compile(PATTERN_STR_IS_INTEGER_GT_ZERO);

    /**
     * 是否 负整数
     */
    public static final String PATTERN_STR_IS_INTEGER_LT_ZERO = "^-[1-9]\\d*$";

    public static final Pattern PATTERN_IS_INTEGER_LT_ZERO = Pattern.compile(PATTERN_STR_IS_INTEGER_LT_ZERO);

    /**
     * 是否 非正整数 <=0
     */
    public static final String PATTERN_STR_IS_INTEGER_LE_ZERO = "^(-[1-9]\\d*|[0]{1})$";

    public static final Pattern PATTERN_IS_INTEGER_LE_ZERO = Pattern.compile(PATTERN_STR_IS_INTEGER_LE_ZERO);

    /**
     * 是否 非负整数 >=0
     */
    public static final String PATTERN_STR_IS_INTEGER_GE_ZERO = "^([1-9]\\d*|[0]{1})$";

    public static final Pattern PATTERN_IS_INTEGER_GE_ZERO = Pattern.compile(PATTERN_STR_IS_INTEGER_GE_ZERO);

    /**
     * 是否 浮点数
     */
    public static final String PATTERN_STR_IS_FLOAT = "^((-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0))|((-?[1-9]\\d*|[0])))$";

    public static final Pattern PATTERN_IS_FLOAT = Pattern.compile(PATTERN_STR_IS_FLOAT);

    /**
     * 是否 正浮点数
     */
    public static final String PATTERN_STR_IS_FLOAT_GT_ZERO = "^(([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*)|([1-9]\\d*))$";

    public static final Pattern PATTERN_IS_FLOAT_GT_ZERO = Pattern.compile(PATTERN_STR_IS_FLOAT_GT_ZERO);

    /**
     * 是否 负浮点数
     */
    public static final String PATTERN_STR_IS_FLOAT_LT_ZERO = "^((-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*))|(-[1-9]\\d*))$";

    public static final Pattern PATTERN_IS_FLOAT_LT_ZERO = Pattern.compile(PATTERN_STR_IS_FLOAT_LT_ZERO);

    /**
     * 是否 非正浮点数 <= 0
     */
    public static final String PATTERN_STR_IS_FLOAT_LE_ZERO = "^(((-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*))|0?\\.0+|0)|(-[1-9]\\d*))$";

    public static final Pattern PATTERN_IS_FLOAT_LE_ZERO = Pattern.compile(PATTERN_STR_IS_FLOAT_LE_ZERO);

    /**
     * 是否 非负浮点数 >= 0
     */
    public static final String PATTERN_STR_IS_FLOAT_GE_ZERO = "^(([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)|([1-9]\\d*))$";

    public static final Pattern PATTERN_IS_FLOAT_GE_ZERO = Pattern.compile(PATTERN_STR_IS_FLOAT_GE_ZERO);

    /**
     * 是否 数字组成
     */
    public static final String PATTERN_STR_IS_NUMBERIC = "^[0-9]*$";

    public static final Pattern PATTERN_IS_NUMBERIC = Pattern.compile(PATTERN_STR_IS_NUMBERIC);

    /**
     * 是否 价格
     * 大于或等于0的两位小数
     */
    public static final String PATTERN_STR_IS_PRICE = "(^[1-9]([0-9]+)?(\\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\\.[0-9]([0-9])?$)";

    public static final Pattern PATTERN_IS_PRICE = Pattern.compile(PATTERN_STR_IS_PRICE);

    /**
     * ip地址
     */
    public static final String PATTERN_STR_IP_ADDRESS = "^((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}$";

    public static final Pattern PATTERN_IP_ADDRESS = Pattern.compile(PATTERN_STR_IP_ADDRESS);

    /**
     * Http Url链接
     */
    public static final String PATTERN_STR_HTTP_URL = "^(https?|http)://((?!(\\?)).)*$";

    public static final Pattern PATTERN_HTTP_URL = Pattern.compile(PATTERN_STR_HTTP_URL);
}
