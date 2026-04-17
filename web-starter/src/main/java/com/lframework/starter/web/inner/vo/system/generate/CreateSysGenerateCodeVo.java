package com.lframework.starter.web.inner.vo.system.generate;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSysGenerateCodeVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 规则ID
   */
  @Schema(description = "规则ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "请输入规则ID！")
  private Integer id;

  /**
   * 名称
   */
  @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入名称！")
  private String name;
}
