package com.lframework.starter.security.bo.system.user;

import com.lframework.starter.mybatis.entity.DefaultSysUser;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserSelectorBo extends BaseBo<DefaultSysUser> {

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
   * 姓名
   */
  @ApiModelProperty("姓名")
  private String name;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  public SysUserSelectorBo() {

  }

  public SysUserSelectorBo(DefaultSysUser dto) {

    super(dto);
  }
}
