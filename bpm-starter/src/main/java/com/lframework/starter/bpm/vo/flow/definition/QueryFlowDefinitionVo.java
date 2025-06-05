package com.lframework.starter.bpm.vo.flow.definition;

import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class QueryFlowDefinitionVo extends PageVo implements Serializable {

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

  /**
   * 版本号
   */
  @ApiModelProperty("版本号")
  private String version;

  /**
   * 是否发布
   */
  @ApiModelProperty("是否发布")
  private Integer isPublish;

  /**
   * 激活状态
   */
  @ApiModelProperty("激活状态")
  private Integer activityStatus;
}
