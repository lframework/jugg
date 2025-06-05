package com.lframework.starter.mq.core.vo;

import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QuerySuccessExportTaskVo extends PageVo {

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;
}
