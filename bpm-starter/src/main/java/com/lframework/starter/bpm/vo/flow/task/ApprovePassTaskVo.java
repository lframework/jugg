package com.lframework.starter.bpm.vo.flow.task;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Map;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApprovePassTaskVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 任务ID
   */
  @Schema(description = "任务ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "任务ID不能为空！")
  private Long taskId;

  /**
   * 说明
   */
  @Schema(description = "说明", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "说明不能为空！")
  private String message;

  /**
   * 表单变量
   */
  @Schema(description = "表单变量")
  private Map<String, Object> variables;
}
