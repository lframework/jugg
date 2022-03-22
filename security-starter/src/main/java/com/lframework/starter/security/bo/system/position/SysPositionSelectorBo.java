package com.lframework.starter.security.bo.system.position;

import com.lframework.starter.security.dto.system.position.DefaultSysPositionDto;
import com.lframework.starter.web.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysPositionSelectorBo extends BaseBo<DefaultSysPositionDto> {

  /**
   * ID
   */
  private String id;

  /**
   * 岗位编号
   */
  private String code;

  /**
   * 岗位名称
   */
  private String name;

  /**
   * 状态
   */
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
