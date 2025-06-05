package com.lframework.starter.bpm.vo.flow.task;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UndoTaskVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 流程实例ID
   */
  @ApiModelProperty(value = "流程实例ID", required = true)
  @NotNull(message = "流程实例ID不能为空！")
  private Long instanceId;

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
