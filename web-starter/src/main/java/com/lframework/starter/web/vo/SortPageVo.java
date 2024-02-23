package com.lframework.starter.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public abstract class SortPageVo extends PageVo {

    private static final long serialVersionUID = 1L;

    /**
     * 排序字段
     */
    @ApiModelProperty("排序字段")
    private String sortField;

    /**
     * 排序类型
     */
    @ApiModelProperty("排序类型")
    private String sortOrder;
}
