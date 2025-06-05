package com.lframework.starter.web.gen.bo.data.entity;

import com.lframework.starter.web.gen.entity.GenDataEntityDetail;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GenDataEntityDetailSelectorBo extends BaseBo<GenDataEntityDetail> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  public GenDataEntityDetailSelectorBo() {
  }

  public GenDataEntityDetailSelectorBo(GenDataEntityDetail dto) {
    super(dto);
  }

  @Override
  protected void afterInit(GenDataEntityDetail dto) {
  }
}
