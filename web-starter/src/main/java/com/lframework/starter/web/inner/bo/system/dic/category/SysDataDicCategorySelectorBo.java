package com.lframework.starter.web.inner.bo.system.dic.category;

import com.lframework.starter.web.inner.entity.SysDataDicCategory;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysDataDicCategorySelectorBo extends BaseBo<SysDataDicCategory> {

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

  public SysDataDicCategorySelectorBo() {
  }

  public SysDataDicCategorySelectorBo(SysDataDicCategory dto) {
    super(dto);
  }
}
