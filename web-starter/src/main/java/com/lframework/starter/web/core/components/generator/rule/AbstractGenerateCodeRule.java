package com.lframework.starter.web.core.components.generator.rule;

import java.io.Serializable;
import lombok.Data;

@Data
public abstract class AbstractGenerateCodeRule implements GenerateCodeRule, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 类型
   */
  private String type;
}
