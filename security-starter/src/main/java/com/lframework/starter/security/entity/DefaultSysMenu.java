package com.lframework.starter.security.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.security.enums.system.SysMenuDisplay;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统菜单
 * </p>
 *
 * @author zmj
 * @since 2021-05-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
public class DefaultSysMenu extends BaseEntity {

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

  /**
   * 创建人ID 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private String createBy;

  /**
   * 创建时间 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  /**
   * 修改人ID 新增和修改时赋值
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private String updateBy;

  /**
   * 修改时间 新增和修改时赋值
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;
}
