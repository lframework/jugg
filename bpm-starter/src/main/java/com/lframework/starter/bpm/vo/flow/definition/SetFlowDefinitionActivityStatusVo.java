package com.lframework.starter.bpm.vo.flow.definition;

import com.lframework.starter.bpm.enums.FlowDefinitionActivityStatus;
import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SetFlowDefinitionActivityStatusVo implements BaseVo, Serializable {

  public static final long serialVersionUID = 1L;

  /**
   * 流程定义ID
   */
  @Schema(description = "流程定义ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "流程定义ID不能为空！")
  private Long id;

  /**
   * 激活状态
   */
  @Schema(description = "激活状态", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "激活状态不能为空！")
  @IsEnum(enumClass = FlowDefinitionActivityStatus.class, message = "激活状态格式不正确！")
  private Integer activityStatus;
}
