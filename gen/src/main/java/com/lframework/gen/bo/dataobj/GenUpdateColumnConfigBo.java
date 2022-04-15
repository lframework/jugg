package com.lframework.gen.bo.dataobj;

import com.lframework.gen.dto.dataobj.GenUpdateColumnConfigDto;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GenUpdateColumnConfigBo extends BaseBo<GenUpdateColumnConfigDto> {

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

  public GenUpdateColumnConfigBo() {

  }

  public GenUpdateColumnConfigBo(GenUpdateColumnConfigDto dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<GenUpdateColumnConfigDto> convert(GenUpdateColumnConfigDto dto) {

    return super.convert(dto);
  }

  @Override
  protected void afterInit(GenUpdateColumnConfigDto dto) {

    super.afterInit(dto);
  }
}
