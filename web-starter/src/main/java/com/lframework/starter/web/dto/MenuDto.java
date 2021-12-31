package com.lframework.starter.web.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 菜单Dto
 *
 * @author zmj
 */
@Data
public class MenuDto implements BaseDto, Serializable {

    /**
     * ID
     */
    private String id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 路径
     */
    private String path;

    /**
     * 是否隐藏
     */
    private Boolean hidden;

    /**
     * 类型
     * 0-目录 1-功能菜单 2-权限
     */
    private Integer display;

    private String component;

    private MenuMetaDto meta;

    /**
     * 父节点ID
     */
    private String parentId;

    /**
     * 是否收藏
     */
    private Boolean isCollect = Boolean.FALSE;
}
