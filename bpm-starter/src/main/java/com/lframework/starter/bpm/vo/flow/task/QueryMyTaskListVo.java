package com.lframework.starter.bpm.vo.flow.task;

import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class QueryMyTaskListVo extends PageVo implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 流程编号
   */
  @ApiModelProperty("流程编号")
  private String flowCode;

  /**
   * 流程名称
   */
  @ApiModelProperty("流程名称")
  private String flowName;

  /**
   * 任务标题
   */
  @ApiModelProperty("任务标题")
  private String title;
}
