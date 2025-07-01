package com.lframework.starter.web.gen.components.custom.page;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.dto.VoidDto;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class CustomPageConfig extends BaseBo<VoidDto> implements Serializable {

  public static final String CACHE_NAME = "CustomPageConfig";

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private Integer id;

  /**
   * 组件配置
   */
  @ApiModelProperty("组件配置")
  private String componentConfig;
}
