package com.lframework.starter.security.dto.system.position;

import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class DefaultSysUserPositionDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    public static final String CACHE_NAME = "DefaultSysUserPositionDto";

    /**
     * ID
     */
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 岗位ID
     */
    private String positionId;
}
