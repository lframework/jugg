package com.lframework.starter.web.inner.bo.auth;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.dto.VoidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CollectMenuBo extends BaseBo<VoidDto> {

    /**
     * ID
     */
    @Schema(description = "ID")
    private String id;

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;

    /**
     * 图标
     */
    @Schema(description = "图标")
    private String icon;

    /**
     * 路由路径
     */
    @Schema(description = "路由路径")
    private String path;
}
