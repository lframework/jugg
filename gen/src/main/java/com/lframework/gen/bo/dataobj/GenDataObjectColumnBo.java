package com.lframework.gen.bo.dataobj;

import com.lframework.gen.dto.dataobj.GenDataObjectColumnDto;
import com.lframework.starter.web.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GenDataObjectColumnBo extends BaseBo<GenDataObjectColumnDto> {

  /**
   * ID
   */
  private String id;

  /**
   * 字段显示名称
   */
  private String name;

  /**
   * 字段名称
   */
  private String columnName;

  /**
   * 是否主键
   */
  private Boolean isKey;

  /**
   * 数据类型
   */
  private Integer dataType;

  /**
   * 排序编号
   */
  private Integer columnOrder;

  /**
   * 备注
   */
  private String description;

  /**
   * 显示类型
   */
  private Integer viewType;

  /**
   * 是否内置枚举
   */
  private Boolean fixEnum;

  /**
   * 后端枚举名
   */
  private String enumBack;

  /**
   * 前端枚举名
   */
  private String enumFront;

  /**
   * 正则表达式
   */
  private String regularExpression;

  /**
   * 是否排序字段
   */
  private Boolean isOrder;

  /**
   * 排序类型
   */
  private String orderType;

  public GenDataObjectColumnBo() {

  }

  public GenDataObjectColumnBo(GenDataObjectColumnDto dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<GenDataObjectColumnDto> convert(GenDataObjectColumnDto dto) {

    return super
        .convert(dto, GenDataObjectColumnBo::getDataType, GenDataObjectColumnBo::getViewType,
            GenDataObjectColumnBo::getOrderType);
  }

  @Override
  protected void afterInit(GenDataObjectColumnDto dto) {

    this.dataType = dto.getDataType().getCode();
    this.viewType = dto.getViewType().getCode();
    this.orderType = dto.getOrderType() == null ? null : dto.getOrderType().getCode();
  }
}
