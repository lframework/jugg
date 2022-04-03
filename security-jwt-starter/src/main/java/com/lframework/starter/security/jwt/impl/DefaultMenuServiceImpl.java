package com.lframework.starter.security.jwt.impl;

import com.lframework.starter.security.impl.AbstractMenuServiceImpl;
import java.util.Collections;
import java.util.Map;

public class DefaultMenuServiceImpl extends AbstractMenuServiceImpl {

  @Override
  protected Map<String, Object> getDefaultVars() {

    return Collections.EMPTY_MAP;
  }
}
