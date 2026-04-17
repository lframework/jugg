package com.lframework.starter.bpm.vo.flow.definition;

import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

@Data
public class QueryFlowDefinitionVo extends PageVo implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 流程编号
   */
  @Schema(description = "流程编号")
  private String code;

  /**
   * 流程名称
   */
  @Schema(description = "流程名称")
  private String name;

  /**
   * 流程分类ID
   */
  @Schema(description = "流程分类ID")
  private String categoryId;

  /**
   * 版本号
   */
  @Schema(description = "版本号")
  private String version;

  /**
   * 是否发布
   */
  @Schema(description = "是否发布")
  private Integer isPublish;

  /**
   * 激活状态
   */
  @Schema(description = "激活状态")
  private Integer activityStatus;
}
