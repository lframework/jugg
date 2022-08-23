package com.lframework.starter.gen.builders;

import com.lframework.common.exceptions.impl.DefaultSysException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.gen.enums.DataObjectType;
import com.lframework.starter.web.utils.ApplicationUtil;
import java.util.Map;

public class TableBuilderFactory {

  public static TableBuilder getBuilder(DataObjectType type) {

    Map<String, TableBuilder> builders = ApplicationUtil.getBeansOfType(TableBuilder.class);
    if (CollectionUtil.isEmpty(builders)) {
      throw new DefaultSysException("没有找到TableBuilder");
    }

    TableBuilder builder = null;
    for (TableBuilder value : builders.values()) {
      if (value.canBuild(type)) {
        builder = value;

        break;
      }
    }
    if (builder == null) {
      throw new DefaultSysException("未知的DataObjectType");

    }

    return builder;
  }
}
