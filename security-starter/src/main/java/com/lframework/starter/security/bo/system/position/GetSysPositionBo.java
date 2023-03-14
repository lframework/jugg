package com.lframework.starter.security.bo.system.position;

import com.lframework.starter.mybatis.entity.DefaultSysPosition;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetSysPositionBo extends BaseBo<DefaultSysPosition> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 岗位编号
   */
  @ApiModelProperty("岗位编号")
  private String code;

  /**
   * 岗位名称
   */
  @ApiModelProperty("岗位名称")
  private String name;

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

  public GetSysPositionBo() {

  }

  public GetSysPositionBo(DefaultSysPosition dto) {

    super(dto);
  }

  @Override
  protected void afterInit(DefaultSysPosition dto) {

  }
}
