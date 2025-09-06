package com.lframework.starter.web.core.utils;

import java.util.Map;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * Spring表达式语言工具类
 * 提供SpEL表达式解析和计算功能，支持动态表达式执行
 * 包括表达式解析、变量绑定、上下文构建等功能
 *
 * @author lframework@163.com
 */
public class SpelUtil {

  private static final SpelExpressionParser PARSER;

  static {
    PARSER = new SpelExpressionParser();
  }

  /**
   * 解析SpEL表达式（使用指定上下文）
   * 使用指定的评估上下文解析表达式
   *
   * @param expression SpEL表达式，不能为null
   * @param ctx 评估上下文，不能为null
   * @return 表达式计算结果
   */
  public static Object parse(String expression, EvaluationContext ctx) {

    return PARSER.parseExpression(expression).getValue(ctx);
  }

  /**
   * 解析SpEL表达式（使用变量映射）
   * 使用变量映射构建上下文并解析表达式
   *
   * @param expression SpEL表达式，不能为null
   * @param vars 变量映射，不能为null
   * @return 表达式计算结果
   */
  public static Object parse(String expression, Map<String, Object> vars) {

    EvaluationContext ctx = buildContext();
    vars.forEach(ctx::setVariable);

    return parse(expression, ctx);
  }

  /**
   * 构建评估上下文
   * 创建标准的SpEL评估上下文
   *
   * @return 评估上下文实例
   */
  public static EvaluationContext buildContext() {

    return new StandardEvaluationContext();
  }
}
