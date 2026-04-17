package com.lframework.starter.web.inner.bo.system.user;

import com.lframework.starter.web.inner.entity.SysUser;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SysUserSelectorBo extends BaseBo<SysUser> {

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
   * 姓名
   */
  @Schema(description = "姓名")
  private String name;

  /**
   * 状态
   */
  @Schema(description = "状态")
  private Boolean available;

  public SysUserSelectorBo() {

  }

  public SysUserSelectorBo(SysUser dto) {

    super(dto);
  }
}
