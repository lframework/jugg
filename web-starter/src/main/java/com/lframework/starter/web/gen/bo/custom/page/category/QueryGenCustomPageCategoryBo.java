package com.lframework.starter.web.gen.bo.custom.page.category;

import com.lframework.starter.web.gen.entity.GenCustomPageCategory;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryGenCustomPageCategoryBo extends BaseBo<GenCustomPageCategory> {

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


  public QueryGenCustomPageCategoryBo() {

  }

  public QueryGenCustomPageCategoryBo(GenCustomPageCategory dto) {

    super(dto);
  }
}
