package com.lframework.starter.web.inner.bo.system.dept;

import com.lframework.starter.web.inner.entity.SysDept;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysDeptSelectorBo extends BaseBo<SysDept> {

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
   * 父级ID
   */
  @ApiModelProperty("父级ID")
  private String parentId;

  public SysDeptSelectorBo() {

  }

  public SysDeptSelectorBo(SysDept dto) {

    super(dto);
  }
}
