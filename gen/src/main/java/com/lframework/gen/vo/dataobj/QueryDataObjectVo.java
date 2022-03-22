package com.lframework.gen.vo.dataobj;

import com.lframework.gen.enums.DataObjectType;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.PageVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询数据对象列表Vo
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryDataObjectVo extends PageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  private String code;

  /**
   * 名称
   */
  private String name;

  /**
   * 类型
   */
  @IsEnum(message = "请选择类型！", enumClass = DataObjectType.class)
  private Integer type;

  /**
   * 状态
   */
  private Boolean available;
}
