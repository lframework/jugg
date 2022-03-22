package com.lframework.starter.web.utils;

import java.util.Map;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * Spel工具类
 *
 * @author zmj
 */
public class SpelUtil {

  private static final SpelExpressionParser PARSER;

  static {
    PARSER = new SpelExpressionParser();
  }

  public static Object parse(String expression, EvaluationContext ctx) {

    return PARSER.parseExpression(expression).getValue(ctx);
  }

  public static Object parse(String expression, Map<String, Object> vars) {

    EvaluationContext ctx = buildContext();
    vars.forEach(ctx::setVariable);

    return parse(expression, ctx);
  }

  public static EvaluationContext buildContext() {

    return new StandardEvaluationContext();
  }
}
