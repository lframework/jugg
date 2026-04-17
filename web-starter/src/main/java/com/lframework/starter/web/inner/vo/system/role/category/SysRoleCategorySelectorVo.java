package com.lframework.starter.web.inner.vo.system.role.category;

import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SysRoleCategorySelectorVo extends PageVo {

  private static final long serialVersionUID = 1L;

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
}
