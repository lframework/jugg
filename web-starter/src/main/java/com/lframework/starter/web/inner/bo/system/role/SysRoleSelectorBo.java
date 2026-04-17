package com.lframework.starter.web.inner.bo.system.role;

import com.lframework.starter.web.inner.entity.SysRole;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SysRoleSelectorBo extends BaseBo<SysRole> {

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
   * 名称
   */
  @Schema(description = "名称")
  private String name;

  public SysRoleSelectorBo() {

  }

  public SysRoleSelectorBo(SysRole dto) {

    super(dto);
  }
}
