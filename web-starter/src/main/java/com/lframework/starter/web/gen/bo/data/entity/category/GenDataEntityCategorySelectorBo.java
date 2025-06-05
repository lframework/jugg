package com.lframework.starter.web.gen.bo.data.entity.category;

import com.lframework.starter.web.gen.entity.GenDataEntityCategory;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GenDataEntityCategorySelectorBo extends BaseBo<GenDataEntityCategory> {

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

  public GenDataEntityCategorySelectorBo() {
  }

  public GenDataEntityCategorySelectorBo(GenDataEntityCategory dto) {
    super(dto);
  }
}
