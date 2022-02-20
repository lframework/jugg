package com.lframework.gen.dto.dataobj;

import com.lframework.gen.components.DetailColumnConfig;
import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class GenDetailColumnConfigDto implements BaseDto, DetailColumnConfig, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 列宽
     */
    private Integer span;

    /**
     * 排序编号
     */
    private Integer orderNo;
}
