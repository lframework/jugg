package com.lframework.starter.web.inner.bo.auth;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.dto.VoidDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CollectMenuBo extends BaseBo<VoidDto> {

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private String id;

    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    private String icon;

    /**
     * 路由路径
     */
    @ApiModelProperty("路由路径")
    private String path;
}
