package com.lframework.starter.web.gen.bo.custom.selector.category;

import com.lframework.starter.web.gen.entity.GenCustomSelectorCategory;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetGenCustomSelectorCategoryBo extends BaseBo<GenCustomSelectorCategory> {

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


  public GetGenCustomSelectorCategoryBo() {

  }

  public GetGenCustomSelectorCategoryBo(GenCustomSelectorCategory dto) {

    super(dto);
  }
}
