package com.lframework.starter.web.components.generator.impl;

import com.lframework.starter.web.components.code.GenerateCodeType;
import com.lframework.starter.web.components.generator.Generator;
import com.lframework.starter.web.config.properties.DefaultSettingProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 默认单号生成器
 *
 * @author zmj
 */
public class DefaultGenerator extends AbstractSnowFlakeGenerator implements Generator {

  private DefaultSettingProperties defaultSettingProperties;

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

    if (defaultSettingProperties.getGeneratorType() == GeneratorType.FLOW) {
      return defaultFlowGenerator.generate();
    } else {
      return defaultSnowFlakeGenerator.generate();
    }
  }

  @Override
  public boolean isSpecial() {

    return true;
  }

  public enum GeneratorType {
    FLOW, SNOW_FLAKE
  }
}
