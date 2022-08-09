package com.lframework.starter.mybatis.dto.system.menu;

import com.lframework.starter.mybatis.enums.system.SysMenuDisplay;
import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class DefaultSysMenuDto implements BaseDto, Serializable {

  public static final String CACHE_NAME = "DefaultSysMenuDto";

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 编号
   */
  private String code;

  /**
   * 名称（前端使用）
   */
  private String name;

  /**
   * 标题
   */
  private String title;

  /**
   * 图标
   */
  private String icon;

  /**
   * 组件（前端使用）
   */
  private String component;

  /**
   * 父级ID
   */
  private String parentId;

  /**
   * 路由路径（前端使用）
   */
  private String path;

  /**
   * 是否缓存（前端使用）
   */
  private Boolean noCache;

  /**
   * 类型 0-目录 1-菜单 2-功能
   */
  private SysMenuDisplay display;

  /**
   * 是否隐藏（前端使用）
   */
  private Boolean hidden;

  /**
   * 权限
   */
  private String permission;

  /**
   * 是否特殊菜单
   */
  private Boolean isSpecial;

  /**
   * 状态
   */
  private Boolean available;

  /**
   * 备注
   */
  private String description;
}
