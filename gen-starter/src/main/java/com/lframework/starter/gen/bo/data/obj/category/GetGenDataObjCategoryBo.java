package com.lframework.starter.gen.bo.data.obj.category;

import com.lframework.starter.gen.entity.GenDataObjCategory;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetGenDataObjCategoryBo extends BaseBo<GenDataObjCategory> {

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


  public GetGenDataObjCategoryBo() {

  }

  public GetGenDataObjCategoryBo(GenDataObjCategory dto) {

    super(dto);
  }
}
