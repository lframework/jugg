package com.lframework.starter.security.bo.system.dept;

import com.lframework.starter.mybatis.entity.DefaultSysDept;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysDeptTreeBo extends BaseBo<DefaultSysDept> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 父级ID
   */
  @ApiModelProperty("父级ID")
  private String parentId;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  public SysDeptTreeBo() {

  }

  public SysDeptTreeBo(DefaultSysDept dto) {

    super(dto);
  }
}
