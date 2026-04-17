package com.lframework.starter.web.inner.vo.system.dept;

import com.lframework.starter.web.core.components.validation.IsCode;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateSysDeptVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED)
  @IsCode
  @NotBlank(message = "编号不能为空！")
  private String code;

  /**
   * 名称
   */
  @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "名称不能为空！")
  private String name;

  /**
   * 简称
   */
  @Schema(description = "简称", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "简称不能为空！")
  private String shortName;

  /**
   * 父级ID
   */
  @Schema(description = "父级ID")
  private String parentId;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;
}
