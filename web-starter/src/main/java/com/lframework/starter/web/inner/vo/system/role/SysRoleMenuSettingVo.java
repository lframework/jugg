package com.lframework.starter.web.inner.vo.system.role;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SysRoleMenuSettingVo implements BaseVo {

  /**
   * 角色ID
   */
  @ApiModelProperty(value = "角色ID", required = true)
  @NotEmpty(message = "角色ID不能为空！")
  private List<String> roleIds;

  /**
   * 菜单ID
   */
  @ApiModelProperty("菜单ID")
  private List<String> menuIds;
}
