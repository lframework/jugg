package com.lframework.starter.bpm.vo.flow.definition;

import com.lframework.starter.bpm.enums.FlowDefinitionIsPublish;
import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SetFlowDefinitionPublishVo implements BaseVo, Serializable {

  public static final long serialVersionUID = 1L;

  /**
   * 流程定义ID
   */
  @ApiModelProperty(value = "流程定义ID", required = true)
  @NotNull(message = "流程定义ID不能为空！")
  private Long id;

  /**
   * 发布状态
   */
  @ApiModelProperty(value = "发布状态", required = true)
  @NotNull(message = "发布状态不能为空！")
  @IsEnum(enumClass = FlowDefinitionIsPublish.class,  message = "发布状态格式不正确！")
  private Integer isPublish;
}
