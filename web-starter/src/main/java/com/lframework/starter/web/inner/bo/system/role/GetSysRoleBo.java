package com.lframework.starter.web.inner.bo.system.role;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.inner.entity.SysRole;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.inner.entity.SysRoleCategory;
import com.lframework.starter.web.inner.service.system.SysRoleCategoryService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetSysRoleBo extends BaseBo<SysRole> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 编号
   */
  @ApiModelProperty("编号")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 分类ID
   */
  @ApiModelProperty("分类ID")
  private String categoryId;

  /**
   * 分类名称
   */
  @ApiModelProperty("分类名称")
  private String categoryName;

  /**
   * 权限
   */
  @ApiModelProperty("权限")
  private String permission;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
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
