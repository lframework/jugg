package com.lframework.starter.web.inner.bo.system.user.group;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.inner.entity.SysUserGroup;
import com.lframework.starter.web.inner.service.system.SysUserGroupDetailService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

/**
 * <p>
 * 用户组 GetBo
 * </p>
 *
 * @author zmj
 */
@Data
public class GetSysUserGroupBo extends BaseBo<SysUserGroup> {

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
   * 用户ID
   */
  @Schema(description = "用户ID")
  private List<String> userIds;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  public GetSysUserGroupBo() {

  }

  public GetSysUserGroupBo(SysUserGroup dto) {

    super(dto);
  }

  @Override
  protected void afterInit(SysUserGroup dto) {

    SysUserGroupDetailService sysUserGroupDetailService = ApplicationUtil.getBean(
        SysUserGroupDetailService.class);
    this.userIds = sysUserGroupDetailService.getUserIdsByGroupId(dto.getId());
  }
}
