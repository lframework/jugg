package com.lframework.gen.dto.dataobj;

import com.lframework.gen.components.QueryColumnConfig;
import com.lframework.gen.enums.GenQueryWidthType;
import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class GenQueryColumnConfigDto implements BaseDto, QueryColumnConfig, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 宽度类型
     */
    private GenQueryWidthType widthType;

    /**
     * 宽度
     */
    private Integer width;

    /**
     * 是否页面排序
     */
    private Boolean sortable;

    /**
     * 排序编号
     */
    private Integer orderNo;
}
