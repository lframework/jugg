package com.lframework.starter.web.inner.bo.system.role;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.inner.entity.SysRole;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.inner.entity.SysRoleCategory;
import com.lframework.starter.web.inner.service.system.SysRoleCategoryService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GetSysRoleBo extends BaseBo<SysRole> {

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
   * 分类ID
   */
  @Schema(description = "分类ID")
  private String categoryId;

  /**
   * 分类名称
   */
  @Schema(description = "分类名称")
  private String categoryName;

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

  public GetSysRoleBo() {

  }

  public GetSysRoleBo(SysRole dto) {

    super(dto);
  }

  @Override
  protected void afterInit(SysRole dto) {
    if (StringUtil.isNotBlank(dto.getCategoryId())) {
      SysRoleCategoryService sysRoleCategoryService = ApplicationUtil.getBean(SysRoleCategoryService.class);
      SysRoleCategory category = sysRoleCategoryService.findById(dto.getCategoryId());
      this.categoryName = category.getName();
    }
  }
}
