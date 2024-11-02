package com.lframework.starter.web.components.generator.rule.impl;

import com.lframework.starter.web.components.generator.rule.GenerateCodeRule;
import java.io.Serializable;
import lombok.Data;

/**
 * 雪花算法生成规则
 */
@Data
public class SnowFlakeGenerateCodeRule implements GenerateCodeRule, Serializable {

  private static final long serialVersionUID = 1L;
}
