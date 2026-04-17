package com.lframework.starter.web.inner.bo.system.user.group;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.inner.entity.SysUserGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 用户组 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
public class QuerySysUserGroupBo extends BaseBo<SysUserGroup> {

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

  public QuerySysUserGroupBo() {

  }

  public QuerySysUserGroupBo(SysUserGroup dto) {

    super(dto);
  }
}
