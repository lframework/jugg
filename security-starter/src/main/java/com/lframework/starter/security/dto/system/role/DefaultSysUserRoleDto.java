package com.lframework.starter.security.dto.system.role;

import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class DefaultSysUserRoleDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 角色ID
     */
    private String roleId;
}
