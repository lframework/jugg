package com.lframework.starter.web.components.generator.handler.impl;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.locker.LockBuilder;
import com.lframework.starter.common.locker.Locker;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.components.generator.handler.GenerateCodeRuleHandler;
import com.lframework.starter.web.components.generator.rule.GenerateCodeRule;
import com.lframework.starter.web.components.generator.rule.impl.FlowGenerateCodeRule;
import com.lframework.starter.web.components.redis.RedisHandler;
import com.lframework.starter.web.components.tenant.TenantContextHolder;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.starter.web.utils.TenantUtil;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;

public class FlowGenerateCodeRuleHandler implements GenerateCodeRuleHandler<FlowGenerateCodeRule> {

  private static final String LOCK_KEY = "flow_generator_index_";

  @Autowired
  private RedisHandler redisHandler;

  @Autowired
  private LockBuilder lockBuilder;

  @Override
  public boolean match(Integer ruleType) {
    return ruleType != null && ruleType == 3;
  }

  @Override
  public boolean match(GenerateCodeRule rule) {
    return rule instanceof FlowGenerateCodeRule;
  }

  @Override
  public String generate(FlowGenerateCodeRule rule) {
    Integer codeLen = rule.getLen() == null ? 1 : rule.getLen();
    String lockerName = LOCK_KEY + rule.getKey();
    String redisKey =
        lockerName + "_" + (TenantUtil.enableTenant() ? TenantContextHolder.getTenantId()
            : "noTenant") + (rule.getExpireType() == 0 ? "_" + DateUtil.formatDate(LocalDate.now())
            : "");
    Locker locker = lockBuilder.buildLocker(redisKey + "_Locker", 60000L, 5000L);
    long no;

    if (locker.lock()) {
      try {
        no = redisHandler.incr(redisKey, rule.getStep());
      } finally {
        locker.unLock();
      }
    } else {
      throw new DefaultClientException("生成单号失败，请稍后重试！");
    }
    redisHandler.expire(redisKey,
        (rule.getExpireType() == 0 ? 24 * 60 * 60 : rule.getExpireSeconds()) * 1000L);

    String noStr = String.valueOf(no);
    if (noStr.length() > codeLen) {
      throw new DefaultSysException("单号超长！");
    }

    StringBuilder builder = new StringBuilder();
    for (int i = 0, len = codeLen - noStr.length(); i < len; i++) {
      builder.append("0");
    }

    return builder.append(noStr).toString();
  }

  @Override
  public String generateExample(FlowGenerateCodeRule rule) {
    Integer codeLen = rule.getLen() == null ? 1 : rule.getLen();
    String noStr = "1";
    StringBuilder builder = new StringBuilder();
    for (int i = 0, len = codeLen - noStr.length(); i < len; i++) {
      builder.append("0");
    }

    return builder.append(noStr).toString();
  }

  @Override
  public FlowGenerateCodeRule parseRule(String json) {
    FlowGenerateCodeRule rule = JsonUtil.parseObject(json, FlowGenerateCodeRule.class);
    if (StringUtil.isBlank(rule.getKey())) {
      rule.setKey(IdUtil.getUUID());
    }

    if (rule.getLen() == null) {
      rule.setLen(10);
    }

    if (rule.getStep() == null) {
      rule.setStep(1);
    }

    if (rule.getExpireType() == null) {
      rule.setExpireType(0);
    }

    if (rule.getExpireSeconds() == null) {
      rule.setExpireSeconds(24 * 60 * 60L);
    }

    return rule;
  }
}
