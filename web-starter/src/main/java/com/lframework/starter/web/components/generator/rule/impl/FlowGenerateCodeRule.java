package com.lframework.starter.web.components.generator.rule.impl;

import com.lframework.starter.web.components.generator.rule.AbstractGenerateCodeRule;
import com.lframework.starter.web.components.generator.rule.GenerateCodeRule;
import java.io.Serializable;
import lombok.Data;

/**
 * 流水号生成规则
 */
@Data
public class FlowGenerateCodeRule extends AbstractGenerateCodeRule implements GenerateCodeRule,
    Serializable {

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
   * 滚动方式 0按天滚动 1按过期秒数滚动
   */
  private Integer expireType;

  /**
   * 过期秒数（过期后会从1重新计数）
   */
  private Long expireSeconds;
}
