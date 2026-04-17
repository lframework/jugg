package com.lframework.starter.web.core.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public abstract class SortPageVo extends PageVo {

    private static final long serialVersionUID = 1L;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段")
    private String sortField;

    /**
     * 排序类型
     */
    @Schema(description = "排序类型")
    private String sortOrder;
}
