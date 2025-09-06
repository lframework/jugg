package com.lframework.starter.common.utils;

import cn.hutool.core.util.ReUtil;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * 基于HuTool的ReUtil进行扩展，提供正则表达式相关的工具方法
 * 包括模式匹配、字符串替换、提取等功能
 *
 * @author lframework@163.com
 */
public class RegUtil {

  /**
   * 判断字符串是否匹配指定的正则表达式模式
   * 使用预编译的Pattern对象进行匹配，提高性能
   *
   * @param pattern 预编译的正则表达式模式，不能为null
   * @param str 要匹配的字符串，不能为null
   * @return true-匹配成功，false-匹配失败
   */
  public static boolean isMatch(Pattern pattern, String str) {

    return ReUtil.isMatch(pattern, str);
  }
}
