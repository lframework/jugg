package com.lframework.starter.web.components.generator.impl;

import com.lframework.starter.web.components.generator.Generator;

public abstract class AbstractGenerator implements Generator {

  @Override
  public boolean isSpecial() {
    return false;
  }
}
