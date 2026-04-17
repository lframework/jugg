package com.lframework.starter.web.inner.bo.system.role;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.inner.entity.SysRole;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QuerySysRoleBo extends BaseBo<SysRole> {

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

  /**
   * 权限
   */
  @Schema(description = "权限")
  private String permission;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  /**
   * 创建人
   */
  @Schema(description = "创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @Schema(description = "创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 修改人
   */
  @Schema(description = "修改人")
  private String updateBy;

  /**
   * 修改时间
   */
  @Schema(description = "修改时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime updateTime;

  public QuerySysRoleBo() {

  }

  public QuerySysRoleBo(SysRole dto) {

    super(dto);
  }

  @Override
  protected void afterInit(SysRole dto) {
  }
}
