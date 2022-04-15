package com.lframework.starter.security.bo.system.menu;

import com.lframework.starter.mybatis.dto.system.menu.DefaultSysMenuDto;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenuSelectorBo extends BaseBo<DefaultSysMenuDto> {

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

  public SysMenuSelectorBo(DefaultSysMenuDto dto) {

    super(dto);
  }
}
