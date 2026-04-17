package com.lframework.starter.web.inner.vo.system.role;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SysRoleMenuSettingVo implements BaseVo {

  /**
   * 角色ID
   */
  @Schema(description = "角色ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotEmpty(message = "角色ID不能为空！")
  private List<String> roleIds;

  /**
   * 菜单ID
   */
  @Schema(description = "菜单ID")
  private List<String> menuIds;
}
