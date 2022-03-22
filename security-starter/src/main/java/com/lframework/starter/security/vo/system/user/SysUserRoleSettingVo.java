package com.lframework.starter.security.vo.system.user;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SysUserRoleSettingVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 用户ID
   */
  @NotEmpty(message = "用户ID不能为空！")
  private List<String> userIds;

  /**
   * 角色ID
   */
  private List<String> roleIds;
}
