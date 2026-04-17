package com.lframework.starter.web.inner.vo.system.generate;

import com.lframework.starter.web.core.components.validation.IsJsonArray;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PreviewSysGenerateCodeVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 配置规则
   */
  @Schema(description = "配置规则", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "配置规则不能为空！")
  @IsJsonArray(message = "配置规则格式错误！")
  private String configStr;
}
