package com.lframework.starter.web.components.generator.handler.impl;

import cn.hutool.core.util.RandomUtil;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.components.generator.handler.GenerateCodeRuleHandler;
import com.lframework.starter.web.components.generator.rule.GenerateCodeRule;
import com.lframework.starter.web.components.generator.rule.impl.CustomRandomStrGenerateCodeRule;
import com.lframework.starter.web.components.generator.rule.impl.RandomIntGenerateCodeRule;
import com.lframework.starter.web.utils.JsonUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomRandomStrGenerateCodeRuleHandler implements
    GenerateCodeRuleHandler<CustomRandomStrGenerateCodeRule> {

  @Override
  public boolean match(Integer ruleType) {
    return ruleType != null && (ruleType == 2 || ruleType == 4);
  }

  @Override
  public boolean match(GenerateCodeRule rule) {
    return rule instanceof CustomRandomStrGenerateCodeRule;
  }

  @Override
  public String generate(CustomRandomStrGenerateCodeRule rule) {
    Integer len = rule.getLen() == null ? 1 : rule.getLen();
    char[] charArr = rule.getPool().toCharArray();
    List<String> pool = new ArrayList<>();
    for (char c : charArr) {
      pool.add(String.valueOf(c));
    }
    List<String> eles = RandomUtil.randomEles(pool, len);
    return StringUtil.join(StringPool.EMPTY_STR, eles);
  }

  @Override
  public String generateExample(CustomRandomStrGenerateCodeRule rule) {
    return generate(rule);
  }

  @Override
  public CustomRandomStrGenerateCodeRule parseRule(String json) {
    Map<String, String> tmpMap = JsonUtil.parseMap(json, String.class, String.class);
    CustomRandomStrGenerateCodeRule rule;
    if ("2".equals(tmpMap.get("type"))) {
      rule = JsonUtil.parseObject(json,
          CustomRandomStrGenerateCodeRule.class);
    } else {
      rule = JsonUtil.parseObject(json,
          RandomIntGenerateCodeRule.class);
    }

    if (rule.getLen() == null) {
      rule.setLen(1);
    }

    return rule;
  }
}
