package com.lframework.starter.web.gen.dto.gen;

import com.lframework.starter.web.gen.components.QueryParamsColumnConfig;
import com.lframework.starter.web.gen.enums.GenQueryType;
import com.lframework.starter.web.core.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class GenQueryParamsColumnConfigDto implements BaseDto, QueryParamsColumnConfig,
    Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 查询类型
   */
  private GenQueryType queryType;

  /**
   * 排序编号
   */
  private Integer orderNo;
}
