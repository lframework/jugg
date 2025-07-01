package com.lframework.starter.web.gen.bo.gen;

import com.lframework.starter.web.gen.dto.gen.GenCreateColumnConfigDto;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GenCreateColumnConfigBo extends BaseBo<GenCreateColumnConfigDto> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 是否必填
   */
  @ApiModelProperty("是否必填")
  private Boolean required;

  /**
   * 排序编号
   */
  @ApiModelProperty("排序编号")
  private Integer orderNo;

  public GenCreateColumnConfigBo() {

  }

  public GenCreateColumnConfigBo(GenCreateColumnConfigDto dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<GenCreateColumnConfigDto> convert(GenCreateColumnConfigDto dto) {

    return super.convert(dto);
  }

  @Override
  protected void afterInit(GenCreateColumnConfigDto dto) {

    super.afterInit(dto);
  }
}
