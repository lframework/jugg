package com.lframework.starter.web.inner.bo.system.menu;

import com.lframework.starter.web.inner.entity.SysMenu;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysMenuSelectorBo extends BaseBo<SysMenu> {

  private static final long serialVersionUID = 1L;

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
   * 父级ID
   */
  @ApiModelProperty("父级ID")
  private String parentId;

  public SysMenuSelectorBo() {

  }

  public SysMenuSelectorBo(SysMenu dto) {

    super(dto);
  }
}
