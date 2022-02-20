package com.lframework.gen.dto.dataobj;

import com.lframework.gen.enums.DataObjectGenStatus;
import com.lframework.gen.enums.DataObjectType;
import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据对象Dto
 */
@Data
public class DataObjectDto implements BaseDto, Serializable {

    /**
     * ID
     */
    private String id;

    /**
     * 编号
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型 1 数据库单表
     */
    private DataObjectType type;

    /**
     * 状态
     */
    private Boolean available;

    /**
     * 状态
     */
    private DataObjectGenStatus genStatus;

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
