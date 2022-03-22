package com.lframework.starter.security.vo.system.role;

import com.lframework.starter.web.vo.BaseVo;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SysRoleMenuSettingVo implements BaseVo {

  /**
   * 角色ID
   */
  @NotEmpty(message = "角色ID不能为空！")
  private List<String> roleIds;

  /**
   * 菜单ID
   */
  private List<String> menuIds;
}
