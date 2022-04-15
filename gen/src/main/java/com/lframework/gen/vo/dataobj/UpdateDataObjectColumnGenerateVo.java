package com.lframework.gen.vo.dataobj;

import com.lframework.gen.enums.GenDataType;
import com.lframework.gen.enums.GenOrderType;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateDataObjectColumnGenerateVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "ID不能为空！")
  private String id;

  /**
   * 字段显示名称
   */
  @ApiModelProperty(value = "字段显示名称", required = true)
  @NotBlank(message = "显示名称不能为空！")
  private String name;

  /**
   * 字段名称
   */
  @ApiModelProperty(value = "字段名称", required = true)
  @NotBlank(message = "属性名不能为空！")
  private String columnName;

  /**
   * 数据类型
   */
  @ApiModelProperty(value = "数据类型", required = true)
  @NotNull(message = "数据类型不能为空！")
  @IsEnum(message = "数据类型格式不正确！", enumClass = GenDataType.class)
  private Integer dataType;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 显示类型
   */
  @ApiModelProperty(value = "显示类型", required = true)
  @NotNull(message = "显示类型不能为空！")
  @IsEnum(message = "显示类型格式不正确！", enumClass = GenDataType.class)
  private Integer viewType;

  /**
   * 是否内置枚举
   */
  @ApiModelProperty(value = "是否内置枚举", required = true)
  @NotNull(message = "是否内置枚举不能为空！")
  private Boolean fixEnum;

  /**
   * 后端枚举名
   */
  @ApiModelProperty("后端枚举名")
  private String enumBack;

  /**
   * 前端枚举名
   */
  @ApiModelProperty("前端枚举名")
  private String enumFront;

  /**
   * 正则表达式
   */
  @ApiModelProperty("正则表达式")
  private String regularExpression;

  /**
   * 是否排序字段
   */
  @ApiModelProperty(value = "是否排序字段", required = true)
  @NotNull(message = "是否排序字段不能为空！")
  private Boolean isOrder;

  /**
   * 排序类型
   */
  @ApiModelProperty("排序类型")
  @IsEnum(message = "排序类型格式不正确！", enumClass = GenOrderType.class)
  private String orderType;
}
