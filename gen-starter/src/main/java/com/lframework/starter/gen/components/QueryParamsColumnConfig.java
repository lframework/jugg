package com.lframework.starter.gen.components;

import com.lframework.starter.gen.enums.GenQueryType;

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
