package com.lframework.starter.web.inner.vo.system.dic.category;

import com.lframework.starter.web.core.components.validation.IsCode;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSysDataDicCategoryVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 租户ID
   */
  @Schema(description = "租户ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "租户ID不能为空！")
  private Integer tenantId;

  /**
   * 编号
   */
  @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入编号！")
  @IsCode
  private String code;

  /**
   * 名称
   */
  @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入名称！")
  private String name;
}
