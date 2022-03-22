package com.lframework.starter.web.components.generator.impl;

import com.lframework.starter.web.components.code.GenerateCodeType;
import com.lframework.starter.web.components.generator.Generator;
import org.springframework.stereotype.Component;

/**
 * 默认流水号生成器 流水号位数是10位，即：yyyyMMdd0000000001格式
 */
@Component
public class DefaultFlowGenerator extends AbstractFlowGenerator implements Generator {

  @Override
  public GenerateCodeType getType() {

    return GenerateCodeType.FLOW;
  }

  @Override
  protected int getCodeLength() {
    return 10;
  }

  @Override
  public boolean isSpecial() {
    return true;
  }
}
