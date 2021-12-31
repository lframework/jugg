package com.lframework.starter.security.vo.system.role;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

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
