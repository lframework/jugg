package com.lframework.starter.web.gen.components;

import com.lframework.starter.web.gen.enums.GenQueryType;

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
