package com.lframework.starter.web.inner.vo.system.generate;

import com.lframework.starter.web.core.components.validation.IsJsonArray;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PreviewSysGenerateCodeVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 配置规则
   */
  @ApiModelProperty(value = "配置规则", required = true)
  @NotBlank(message = "配置规则不能为空！")
  @IsJsonArray(message = "配置规则格式错误！")
  private String configStr;
}
