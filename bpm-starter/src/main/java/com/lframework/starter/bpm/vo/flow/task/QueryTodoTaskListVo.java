package com.lframework.starter.bpm.vo.flow.task;

import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

@Data
public class QueryTodoTaskListVo extends PageVo implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 流程编号
   */
  @Schema(description = "流程编号")
  private String flowCode;

  /**
   * 流程名称
   */
  @Schema(description = "流程名称")
  private String flowName;

  /**
   * 任务标题
   */
  @Schema(description = "任务标题")
  private String title;
}
