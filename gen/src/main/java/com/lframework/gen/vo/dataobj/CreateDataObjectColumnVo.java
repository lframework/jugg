package com.lframework.gen.vo.dataobj;

import com.lframework.gen.enums.GenDataType;
import com.lframework.gen.enums.GenViewType;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
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
  private String id;

  /**
   * 数据对象ID
   */
  @NotBlank(message = "数据对象ID不能为空！")
  private String dataObjId;

  /**
   * 字段显示名称
   */
  @NotBlank(message = "字段名称不能为空！")
  private String name;

  /**
   * 字段属性名称
   */
  @NotBlank(message = "字段属性不能为空！")
  private String propertyName;

  /**
   * 是否主键
   */
  private Boolean isKey;

  /**
   * 数据类型
   *
   * @see GenDataType
   */
  @NotNull(message = "数据类型不能为空！")
  @IsEnum(message = "数据类型不能为空！", enumClass = GenDataType.class)
  private Integer dataType;

  /**
   * 显示类型
   */
  @NotNull(message = "显示类型不能为空！")
  @IsEnum(message = "显示类型不能为空！", enumClass = GenViewType.class)
  private Integer viewType;

  /**
   * 排序编号
   */
  @NotNull(message = "排序编号不能为空！")
  private Integer columnOrder;

  /**
   * 备注
   */
  private String description;
}
