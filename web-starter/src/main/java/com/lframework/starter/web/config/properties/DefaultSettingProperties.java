package com.lframework.starter.web.config.properties;

import com.lframework.starter.web.components.generator.impl.DefaultGenerator.GeneratorType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jugg.default-setting")
public class DefaultSettingProperties {

  /**
   * 默认的用户ID，用于写入数据库时的createById和updateById的自动赋值，如果有登录人则取登录人ID，如果没有则取默认用户ID，如果不配置，默认是1
   */
  private String defaultUserId = "1";

  /**
   * 默认的用户姓名，用于写入数据库时的createBy和updateBy的自动赋值，如果有登录人则取登录人姓名，如果没有则取默认用户姓名，如果不配置，默认是系统管理员
   */
  private String defaultUserName = "系统管理员";

  /**
   * 默认单号生成类型 FLOW：流水号 SNOW_FLAKE：雪花算法，如果不配置，默认是SNOW_FLAKE
   */
  private GeneratorType generatorType = GeneratorType.SNOW_FLAKE;
}
