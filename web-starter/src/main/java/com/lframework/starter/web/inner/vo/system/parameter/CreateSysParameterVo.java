package com.lframework.starter.web.inner.vo.system.parameter;

import com.lframework.starter.web.core.components.validation.Regex;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSysParameterVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 租户ID
   */
  @Schema(description = "租户ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "租户ID不能为空！")
  private Integer tenantId;

  /**
   * 键
   */
  @Schema(description = "键", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入键！")
  @Regex(regexp = "^[A-Za-z0-9\\.\\-_]+$", message = "键只能由大写字母、小写字母、数字或._-组成")
  private String pmKey;

  /**
   * 值
   */
  @Schema(description = "值")
  private String pmValue;

  /**
   * 是否加密值
   */
  @Schema(description = "是否加密值", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "请选择是否加密值！")
  private Boolean isEncrypt;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

}
