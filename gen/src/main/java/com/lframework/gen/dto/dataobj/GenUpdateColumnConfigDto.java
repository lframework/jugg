package com.lframework.gen.dto.dataobj;

import com.lframework.gen.components.UpdateColumnConfig;
import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class GenUpdateColumnConfigDto implements BaseDto, UpdateColumnConfig, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 是否必填
     */
    private Boolean required;

    /**
     * 排序编号
     */
    private Integer orderNo;
}
