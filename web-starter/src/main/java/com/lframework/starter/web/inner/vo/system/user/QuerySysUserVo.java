package com.lframework.starter.web.inner.vo.system.user;

import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.core.vo.SortPageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

@Data
public class QuerySysUserVo extends SortPageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @Schema(description = "编号")
  private String code;

  /**
   * 用户名
   */
  @Schema(description = "用户名")
  private String username;

  /**
   * 姓名
   */
  @Schema(description = "姓名")
  private String name;

  /**
   * 部门ID
   */
  @Schema(description = "部门ID")
  private String deptId;

  /**
   * 角色ID
   */
  @Schema(description = "角色ID")
  private String roleId;

  /**
   * 是否锁定
   */
  @Schema(description = "是否锁定")
  private Boolean lockStatus;
}
