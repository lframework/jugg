package com.lframework.starter.bpm.vo.flow.task;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApprovePassTaskVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 任务ID
   */
  @ApiModelProperty(value = "任务ID", required = true)
  @NotNull(message = "任务ID不能为空！")
  private Long taskId;

  /**
   * 说明
   */
  @ApiModelProperty(value = "说明", required = true)
  @NotBlank(message = "说明不能为空！")
  private String message;

  /**
   * 表单变量
   */
  @ApiModelProperty("表单变量")
  private Map<String, Object> variables;
}
