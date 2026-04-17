package com.lframework.starter.mq.core.vo;

import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryFailExportTaskVo extends PageVo {

  /**
   * 名称
   */
  @Schema(description = "名称")
  private String name;
}
