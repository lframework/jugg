package com.lframework.starter.web.inner.bo.system.menu;

import com.lframework.starter.web.inner.entity.SysMenu;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QuerySysMenuBo extends BaseBo<SysMenu> {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 编号
   */
  @Schema(description = "编号")
  private String code;

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
   * 父级ID
   */
  @Schema(description = "父级ID")
  private String parentId;

  /**
   * 类型
   */
  @Schema(description = "类型")
  private Integer display;

  /**
   * 权限
   */
  @Schema(description = "权限")
  private String permission;

  /**
   * 是否特殊菜单
   */
  @Schema(description = "是否特殊菜单")
  private Boolean isSpecial;

  /**
   * 状态
   */
  @Schema(description = "状态")
  private Boolean available;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  public QuerySysMenuBo() {

  }

  public QuerySysMenuBo(SysMenu dto) {

    super(dto);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A> BaseBo<SysMenu> convert(SysMenu dto) {

    return super.convert(dto, QuerySysMenuBo::getDisplay);
  }

  @Override
  protected void afterInit(SysMenu dto) {

    this.display = dto.getDisplay().getCode();
  }
}
