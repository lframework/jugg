package com.lframework.starter.security.bo.system.position;

import com.lframework.starter.mybatis.dto.system.position.DefaultSysPositionDto;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysPositionSelectorBo extends BaseBo<DefaultSysPositionDto> {

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
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  public SysPositionSelectorBo() {

  }

  public SysPositionSelectorBo(DefaultSysPositionDto dto) {

    super(dto);
  }

  @Override
  protected void afterInit(DefaultSysPositionDto dto) {

  }
}
