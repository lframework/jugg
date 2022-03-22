package com.lframework.gen.bo.simpledb;

import com.lframework.gen.dto.simpledb.SimpleTableColumnDto;
import com.lframework.starter.web.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetSimpleTableColumnBo extends BaseBo<SimpleTableColumnDto> {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 表ID
   */
  private String tableId;

  /**
   * 字段名
   */
  private String columnName;

  /**
   * 字段数据类型
   */
  private Integer dataType;

  /**
   * 是否允许为空
   */
  private String isNullable;

  /**
   * 是否主键
   */
  private Boolean isKey;

  /**
   * 默认值
   */
  private String columnDefault;

  /**
   * 字段排序
   */
  private Long ordinalPosition;

  /**
   * 字段备注
   */
  private String columnComment;

  public GetSimpleTableColumnBo() {

  }

  public GetSimpleTableColumnBo(SimpleTableColumnDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<SimpleTableColumnDto> convert(SimpleTableColumnDto dto) {

    return super.convert(dto, GetSimpleTableColumnBo::getDataType);
  }

  @Override
  protected void afterInit(SimpleTableColumnDto dto) {

    this.dataType = dto.getDataType().getCode();
  }
}
