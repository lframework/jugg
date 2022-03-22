package com.lframework.starter.web.components.generator.impl;

import com.lframework.starter.web.components.code.GenerateCodeType;
import com.lframework.starter.web.components.generator.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 默认单号生成器
 *
 * @author zmj
 */
@Component
public class DefaultGenerator extends AbstractSnowFlakeGenerator implements Generator {

  @Value("${default-setting.generator-type:'SNOW_FLAKE'}")
  private GeneratorType generatorType;

  @Autowired
  private DefaultFlowGenerator defaultFlowGenerator;

  @Autowired
  private DefaultSnowFlakeGenerator defaultSnowFlakeGenerator;

  @Override
  public GenerateCodeType getType() {

    return GenerateCodeType.DEFAULT;
  }

  @Override
  public String generate() {
    if (this.generatorType == GeneratorType.FLOW) {
      return defaultFlowGenerator.generate();
    } else {
      return defaultSnowFlakeGenerator.generate();
    }
  }

  public enum GeneratorType {
    FLOW, SNOW_FLAKE
  }

  @Override
  public boolean isSpecial() {
    return true;
  }
}
