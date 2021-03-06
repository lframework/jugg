package com.lframework.gen.vo.dataobj;

import com.lframework.gen.enums.DataObjectType;
import com.lframework.starter.web.components.validation.IsCode;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 创建数据对象Vo
 */
@Data
public class CreateDataObjectVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @ApiModelProperty(value = "编号", required = true)
  @IsCode
  @NotBlank(message = "请输入编号！")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * 类型
   */
  @ApiModelProperty(value = "类型", required = true)
  @NotNull(message = "请选择类型！")
  @IsEnum(message = "请选择类型！", enumClass = DataObjectType.class)
  private Integer type;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}
