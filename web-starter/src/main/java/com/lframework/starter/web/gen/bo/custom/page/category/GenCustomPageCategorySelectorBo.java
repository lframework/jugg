package com.lframework.starter.web.gen.bo.custom.page.category;

import com.lframework.starter.web.gen.entity.GenCustomPageCategory;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GenCustomPageCategorySelectorBo extends BaseBo<GenCustomPageCategory> {

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

  public GenCustomPageCategorySelectorBo() {
  }

  public GenCustomPageCategorySelectorBo(GenCustomPageCategory dto) {
    super(dto);
  }
}
