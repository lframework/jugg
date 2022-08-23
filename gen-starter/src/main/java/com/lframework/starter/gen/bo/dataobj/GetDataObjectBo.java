package com.lframework.starter.gen.bo.dataobj;

import com.lframework.starter.gen.dto.dataobj.DataObjectDto;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetDataObjectBo extends BaseBo<DataObjectDto> {

  private static final long serialVersionUID = 1L;

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
   * 类型 1 数据库单表
   */
  @ApiModelProperty("类型 1 数据库单表")
  private Integer type;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  public GetDataObjectBo() {

  }

  public GetDataObjectBo(DataObjectDto dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<DataObjectDto> convert(DataObjectDto dto) {

    return super.convert(dto, GetDataObjectBo::getType);
  }

  @Override
  protected void afterInit(DataObjectDto dto) {

    this.type = dto.getType().getCode();
  }
}
