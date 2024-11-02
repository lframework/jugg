package com.lframework.starter.web.components.generator.rule.impl;

import com.lframework.starter.web.components.generator.rule.GenerateCodeRule;
import java.io.Serializable;
import lombok.Data;

/**
 * UUID生成规则
 */
@Data
public class UUIDGenerateCodeRule implements GenerateCodeRule, Serializable {

  private static final long serialVersionUID = 1L;
}
