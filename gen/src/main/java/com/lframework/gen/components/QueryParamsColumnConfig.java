package com.lframework.gen.components;

import com.lframework.gen.enums.GenQueryType;

public interface QueryParamsColumnConfig {

  /**
   * 查询类型
   *
   * @return
   */
  GenQueryType getQueryType();

  /**
   * 排序
   *
   * @return
   */
  Integer getOrderNo();
}
