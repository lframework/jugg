package com.lframework.starter.security.bo.system.role;

import com.lframework.starter.security.dto.system.role.DefaultSysRoleDto;
import com.lframework.starter.web.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetSysRoleBo extends BaseBo<DefaultSysRoleDto> {

    /**
     * ID
     */
    private String id;

    /**
     * 岗位编号
     */
    private String code;

    /**
     * 岗位名称
     */
    private String name;

    /**
     * 权限
     */
    private String permission;

    /**
     * 状态
     */
    private Boolean available;

    /**
     * 备注
     */
    private String description;

    public GetSysRoleBo() {

    }

    public GetSysRoleBo(DefaultSysRoleDto dto) {

        super(dto);
    }
}
