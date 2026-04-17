package com.lframework.starter.web.inner.vo.system.user;

import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

@Data
public class SysUserSelectorVo extends PageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

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
   * 用户名
   */
  @Schema(description = "用户名")
  private String username;

  /**
   * 状态
   */
  @Schema(description = "状态")
  private Boolean available;
}
