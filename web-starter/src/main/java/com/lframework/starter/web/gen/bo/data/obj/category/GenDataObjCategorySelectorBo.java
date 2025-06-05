package com.lframework.starter.web.gen.bo.data.obj.category;

import com.lframework.starter.web.gen.entity.GenDataObjCategory;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GenDataObjCategorySelectorBo extends BaseBo<GenDataObjCategory> {

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

  public GenDataObjCategorySelectorBo() {
  }

  public GenDataObjCategorySelectorBo(GenDataObjCategory dto) {
    super(dto);
  }
}
