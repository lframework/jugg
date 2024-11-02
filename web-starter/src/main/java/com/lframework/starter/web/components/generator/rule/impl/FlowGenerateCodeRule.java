package com.lframework.starter.web.components.generator.rule.impl;

import com.lframework.starter.web.components.generator.rule.GenerateCodeRule;
import java.io.Serializable;
import lombok.Data;

/**
 * 流水号生成规则
 */
@Data
public class FlowGenerateCodeRule implements GenerateCodeRule, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 唯一标识
   */
  private String key;

  /**
   * 长度
   */
  private Integer len;

  /**
   * 步长
   */
  private Integer step;

  /**
   * 过期秒数（过期后会从1重新计数）
   */
  private Long expireSeconds;
}
