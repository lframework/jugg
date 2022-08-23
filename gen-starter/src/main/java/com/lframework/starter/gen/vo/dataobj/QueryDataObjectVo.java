package com.lframework.starter.gen.vo.dataobj;

import com.lframework.starter.gen.enums.DataObjectType;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
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
  @ApiModelProperty("编号")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 类型
   */
  @ApiModelProperty("类型")
  @IsEnum(message = "请选择类型！", enumClass = DataObjectType.class)
  private Integer type;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;
}
