package com.lframework.starter.web.inner.vo.system.menu;

import com.lframework.starter.web.inner.enums.system.SysMenuDisplay;
import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class SysMenuSelectorVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("类型")
  @IsEnum(message = "类型格式不正确！", enumClass = SysMenuDisplay.class)
  private Integer display;

  @ApiModelProperty("状态")
  private Boolean available;
}
