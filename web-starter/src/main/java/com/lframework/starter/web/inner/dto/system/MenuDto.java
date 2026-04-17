package com.lframework.starter.web.inner.dto.system;

import com.lframework.starter.web.core.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

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
  @Schema(description = "ID")
  private String id;

  /**
   * 菜单名称
   */
  @Schema(description = "菜单名称")
  private String name;

  /**
   * 路径
   */
  @Schema(description = "路径")
  private String path;

  /**
   * 是否隐藏
   */
  @Schema(description = "是否隐藏")
  private Boolean hidden;

  /**
   * 类型 0-目录 1-功能菜单 2-权限
   */
  @Schema(description = "类型 0-目录 1-功能菜单 2-权限")
  private Integer display;

  /**
   * 组件类型
   */
  @Schema(description = "组件类型")
  private Integer componentType;

  /**
   * 组件
   */
  @Schema(description = "组件")
  private String component;

  /**
   * 自定义请求参数
   */
  @Schema(description = "自定义请求参数")
  private String requestParam;


  /**
   * meta
   */
  @Schema(description = "meta")
  private MenuMetaDto meta;

  /**
   * 父节点ID
   */
  @Schema(description = "父节点ID")
  private String parentId;

  /**
   * 是否收藏
   */
  @Schema(description = "是否收藏")
  private Boolean isCollect = Boolean.FALSE;
}
