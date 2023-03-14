package com.lframework.starter.security.bo.system.dept;

import com.lframework.starter.mybatis.entity.DefaultSysDept;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetSysDeptBo extends BaseBo<DefaultSysDept> {

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

  /**
   * 简称
   */
  @ApiModelProperty("简称")
  private String shortName;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  public GetSysDeptBo() {

  }

  public GetSysDeptBo(DefaultSysDept dto) {

    super(dto);
  }
}
