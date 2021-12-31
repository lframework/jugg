package com.lframework.starter.security.dto.system.dept;

import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class DefaultSysUserDeptDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    public static final String CACHE_NAME = "DefaultSysUserDeptDto";

    /**
     * ID
     */
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 部门ID
     */
    private String deptId;
}
