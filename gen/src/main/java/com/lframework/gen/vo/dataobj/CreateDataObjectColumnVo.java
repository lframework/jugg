package com.lframework.gen.vo.dataobj;

import com.lframework.gen.enums.GenDataType;
import com.lframework.gen.enums.GenViewType;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 创建数据对象列Vo
 */
@Data
public class CreateDataObjectColumnVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 数据对象ID
   */
  @ApiModelProperty(value = "数据对象ID", required = true)
  @NotBlank(message = "数据对象ID不能为空！")
  private String dataObjId;

  /**
   * 字段显示名称
   */
  @ApiModelProperty(value = "字段显示名称", required = true)
  @NotBlank(message = "字段名称不能为空！")
  private String name;

  /**
   * 字段属性名称
   */
  @ApiModelProperty(value = "字段属性名称", required = true)
  @NotBlank(message = "字段属性不能为空！")
  private String propertyName;

  /**
   * 是否主键
   */
  @ApiModelProperty("是否主键")
  private Boolean isKey;

  /**
   * 数据类型
   *
   * @see GenDataType
   */
  @ApiModelProperty(value = "数据类型", required = true)
  @NotNull(message = "数据类型不能为空！")
  @IsEnum(message = "数据类型不能为空！", enumClass = GenDataType.class)
  private Integer dataType;

  /**
   * 显示类型
   */
  @ApiModelProperty(value = "显示类型", required = true)
  @NotNull(message = "显示类型不能为空！")
  @IsEnum(message = "显示类型不能为空！", enumClass = GenViewType.class)
  private Integer viewType;

  /**
   * 排序编号
   */
  @ApiModelProperty(value = "排序编号", required = true)
  @NotNull(message = "排序编号不能为空！")
  private Integer columnOrder;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}
