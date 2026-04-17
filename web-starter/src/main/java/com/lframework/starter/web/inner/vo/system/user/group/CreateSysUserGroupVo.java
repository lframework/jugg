package com.lframework.starter.web.inner.vo.system.user.group;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateSysUserGroupVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "编号不能为空！")
  private String code;

  /**
   * 名称
   */
  @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "名称不能为空！")
  private String name;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  /**
   * 用户ID
   */
  @Schema(description = "用户ID")
  private List<String> userIds;
}
