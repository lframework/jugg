package com.lframework.starter.web.core.components.generator;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.components.generator.handler.GenerateCodeRuleHandler;
import com.lframework.starter.web.core.components.generator.rule.GenerateCodeRule;
import com.lframework.starter.web.core.utils.JsonUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 单号生成器Factory
 *
 * @author zmj
 */
public class GenerateCodeFactory {

  public static String generate(String configStr) {
    return generate(getRules(configStr));
  }

  public static String generate(List<GenerateCodeRule> ruleList) {
    Map<String, GenerateCodeRuleHandler> handlerMap = ApplicationUtil.getBeansOfType(
        GenerateCodeRuleHandler.class);
    Collection<GenerateCodeRuleHandler> handlerList = handlerMap.values();

    StringBuilder builder = new StringBuilder();
    for (GenerateCodeRule rule : ruleList) {
      for (GenerateCodeRuleHandler handler : handlerList) {
        if (handler.match(rule)) {
          builder.append(handler.generate(rule));
        }
      }
    }

    return builder.toString();
  }

  public static String generateExample(String configStr) {
    return generateExample(getRules(configStr));
  }

  public static String generateExample(List<GenerateCodeRule> ruleList) {
    Map<String, GenerateCodeRuleHandler> handlerMap = ApplicationUtil.getBeansOfType(
        GenerateCodeRuleHandler.class);
    Collection<GenerateCodeRuleHandler> handlerList = handlerMap.values();

    StringBuilder builder = new StringBuilder();
    for (GenerateCodeRule rule : ruleList) {
      for (GenerateCodeRuleHandler handler : handlerList) {
        if (handler.match(rule)) {
          builder.append(handler.generateExample(rule));
        }
      }
    }

    return builder.toString();
  }

  public static List<GenerateCodeRuleHandler> getHandlers(String configStr) {

    Map<String, GenerateCodeRuleHandler> handlerMap = ApplicationUtil.getBeansOfType(
        GenerateCodeRuleHandler.class);

    JSONArray configArr = JsonUtil.parseArray(configStr);

    List<GenerateCodeRuleHandler> results = new ArrayList<>();

    for (int i = 0; i < configArr.size(); i++) {
      JSONObject config = configArr.getJSONObject(i);

      Integer type = config.getInt("type");
      if (type == null) {
        throw new DefaultSysException("configStr {} 配置信息错误！");
      }

      Collection<GenerateCodeRuleHandler> handlerList = handlerMap.values();
      for (GenerateCodeRuleHandler handler : handlerList) {
        if (handler.match(type)) {
          results.add(handler);
        }
      }
    }

    if (results.isEmpty()) {
      throw new DefaultSysException("configStr {} 配置信息错误！");
    }

    return results;
  }

  public static List<GenerateCodeRule> getRules(String configStr) {
    List<GenerateCodeRuleHandler> ruleHandlerList = GenerateCodeFactory.getHandlers(
        configStr);
    JSONArray configArr = JsonUtil.parseArray(configStr);

    List<GenerateCodeRule> results = new ArrayList<>();

    for (int i = 0; i < ruleHandlerList.size(); i++) {
      GenerateCodeRuleHandler ruleHandler = ruleHandlerList.get(i);
      results.add(ruleHandler.parseRule(configArr.getStr(i)));
    }

    return results;
  }
}
