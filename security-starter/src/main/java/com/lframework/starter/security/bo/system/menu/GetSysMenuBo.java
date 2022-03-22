package com.lframework.starter.security.bo.system.menu;

import com.lframework.common.utils.StringUtil;
import com.lframework.starter.security.dto.system.menu.DefaultSysMenuDto;
import com.lframework.starter.security.service.system.ISysMenuService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetSysMenuBo extends BaseBo<DefaultSysMenuDto> {

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
   * 父级名称
   */
  private String parentName;

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
  private Integer display;

  /**
   * 是否隐藏（前端使用）
   */
  private Boolean hidden;

  /**
   * 权限
   */
  private String permission;

  /**
   * 状态
   */
  private Boolean available;

  /**
   * 备注
   */
  private String description;

  public GetSysMenuBo() {

  }

  public GetSysMenuBo(DefaultSysMenuDto dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<DefaultSysMenuDto> convert(DefaultSysMenuDto dto) {

    return super.convert(dto, GetSysMenuBo::getDisplay);
  }

  @Override
  protected void afterInit(DefaultSysMenuDto dto) {

    this.display = dto.getDisplay().getCode();
    if (!StringUtil.isBlank(dto.getParentId())) {
      ISysMenuService sysMenuService = ApplicationUtil.getBean(ISysMenuService.class);
      this.parentName = sysMenuService.getById(dto.getParentId()).getTitle();
    }

  }
}
