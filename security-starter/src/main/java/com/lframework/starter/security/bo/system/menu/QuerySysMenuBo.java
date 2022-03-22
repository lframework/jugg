package com.lframework.starter.security.bo.system.menu;

import com.lframework.starter.security.dto.system.menu.DefaultSysMenuDto;
import com.lframework.starter.web.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuerySysMenuBo extends BaseBo<DefaultSysMenuDto> {

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
   * 标题
   */
  private String title;

  /**
   * 父级ID
   */
  private String parentId;

  /**
   * 类型 0-目录 1-菜单 2-功能
   */
  private Integer display;

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

  public QuerySysMenuBo() {

  }

  public QuerySysMenuBo(DefaultSysMenuDto dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<DefaultSysMenuDto> convert(DefaultSysMenuDto dto) {

    return super.convert(dto, QuerySysMenuBo::getDisplay);
  }

  @Override
  protected void afterInit(DefaultSysMenuDto dto) {

    this.display = dto.getDisplay().getCode();
  }
}
