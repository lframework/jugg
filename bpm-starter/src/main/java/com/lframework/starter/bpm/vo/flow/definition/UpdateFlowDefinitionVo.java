package com.lframework.starter.bpm.vo.flow.definition;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateFlowDefinitionVo implements BaseVo, Serializable {

  public static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotNull(message = "ID不能为空！")
  private Long id;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  @NotBlank(message = "名称不能为空！")
  private String name;

  /**
   * 分类ID
   */
  @ApiModelProperty(value = "分类ID", required = true)
  @NotBlank(message = "分类ID不能为空！")
  private String categoryId;
}
