package com.lframework.starter.gen.dto.dataobj;

import com.lframework.starter.gen.components.QueryParamsColumnConfig;
import com.lframework.starter.gen.enums.GenQueryType;
import com.lframework.starter.web.dto.BaseDto;
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
