package com.lframework.starter.security.dto.system.position;

import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class DefaultSysPositionDto implements BaseDto, Serializable {

    public static final String CACHE_NAME = "DefaultSysPositionDto";

    private static final long serialVersionUID = 1L;

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
     * 状态
     */
    private Boolean available;

    /**
     * 备注
     */
    private String description;

    /**
     * 创建人ID
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
