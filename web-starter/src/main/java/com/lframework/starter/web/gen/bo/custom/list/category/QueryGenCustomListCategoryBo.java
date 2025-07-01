package com.lframework.starter.web.gen.bo.custom.list.category;

import com.lframework.starter.web.gen.entity.GenCustomListCategory;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryGenCustomListCategoryBo extends BaseBo<GenCustomListCategory> {

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


  public QueryGenCustomListCategoryBo() {

  }

  public QueryGenCustomListCategoryBo(GenCustomListCategory dto) {

    super(dto);
  }
}
