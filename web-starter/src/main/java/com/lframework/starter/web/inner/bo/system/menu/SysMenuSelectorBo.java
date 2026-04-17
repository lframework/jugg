package com.lframework.starter.web.inner.bo.system.menu;

import com.lframework.starter.web.inner.entity.SysMenu;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SysMenuSelectorBo extends BaseBo<SysMenu> {

  private static final long serialVersionUID = 1L;

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
   * 父级ID
   */
  @Schema(description = "父级ID")
  private String parentId;

  public SysMenuSelectorBo() {

  }

  public SysMenuSelectorBo(SysMenu dto) {

    super(dto);
  }
}
