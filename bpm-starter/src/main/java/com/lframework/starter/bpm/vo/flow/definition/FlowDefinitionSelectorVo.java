package com.lframework.starter.bpm.vo.flow.definition;

import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class FlowDefinitionSelectorVo extends PageVo implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 流程编号
   */
  @ApiModelProperty("流程编号")
  private String code;

  /**
   * 流程名称
   */
  @ApiModelProperty("流程名称")
  private String name;

  /**
   * 流程分类ID
   */
  @ApiModelProperty("流程分类ID")
  private String categoryId;
}
