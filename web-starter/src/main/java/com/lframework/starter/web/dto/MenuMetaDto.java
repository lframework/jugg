package com.lframework.starter.web.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 菜单Meta Dto
 *
 * @author zmj
 */
@Data
public class MenuMetaDto implements BaseDto, Serializable {

    /**
     * 标题
     */
    private String title;

    /**
     * 是否缓存
     */
    private Boolean noCache;
}
