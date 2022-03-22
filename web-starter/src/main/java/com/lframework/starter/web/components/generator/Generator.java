package com.lframework.starter.web.components.generator;

import com.lframework.starter.web.components.code.GenerateCodeType;

/**
 * 单号生成器 根据#getType进行类型比对 比对成功后，#generate进行单号生成 如果需要自定义单号生成器规则，步骤如下： 1、实现GenerateCodeType接口，定义类型
 *
 * @see GenerateCodeType 2、实现Generator接口，定义生成器，然后注册成Bean
 */
public interface Generator {

  /**
   * 获取类型
   *
   * @return
   */
  GenerateCodeType getType();

  /**
   * 生成code
   *
   * @return
   */
  String generate();

  /**
   * 是否内置生成器 用于区分是内置的还是自定义的生成器
   *
   * @return
   */
  boolean isSpecial();
}
