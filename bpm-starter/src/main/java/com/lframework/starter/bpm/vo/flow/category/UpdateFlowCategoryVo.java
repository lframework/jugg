package com.lframework.starter.bpm.vo.flow.category;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class UpdateFlowCategoryVo implements BaseVo, Serializable {

  public static final long serialVersionUID = 1L;
  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  private String id;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  private String name;
}
