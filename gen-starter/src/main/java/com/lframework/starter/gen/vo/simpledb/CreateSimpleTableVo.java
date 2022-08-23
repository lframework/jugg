package com.lframework.starter.gen.vo.simpledb;

import com.lframework.starter.gen.enums.GenConvertType;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author: zg
 * @create: 2021/06/02 15:38
 * @description:
 */
@Data
public class CreateSimpleTableVo implements BaseVo, Serializable {


  private static final long serialVersionUID = -1408536300284091591L;

  /**
   * 数据对象ID
   */
  @ApiModelProperty("数据对象ID")
  private String dataObjId;

  /**
   * 转换方式
   */
  @ApiModelProperty(value = "转换方式", required = true)
  @NotNull(message = "请选择转换方式！")
  @IsEnum(message = "请选择转换方式！", enumClass = GenConvertType.class)
  private Integer convertType;

  /**
   * 数据表所属的数据库名
   */
  @ApiModelProperty(value = "数据表所属的数据库名", required = true)
  @NotBlank(message = "请输入数据库名！")
  private String tableSchema;

  /**
   * 数据库表名
   */
  @ApiModelProperty(value = "数据库表名", required = true)
  @NotBlank(message = "请输入数据库表名！")
  private String tableName;
}
