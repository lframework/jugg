package com.lframework.starter.web.gen.vo;

import com.lframework.starter.web.gen.enums.GenQueryType;
import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateQueryParamsColumnConfigVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotNull(message = "ID不能为空！")
  private String id;

  /**
   * 查询类型
   */
  @ApiModelProperty(value = "查询类型", required = true)
  @NotNull(message = "查询类型不能为空！")
  @IsEnum(message = "查询类型不能为空！", enumClass = GenQueryType.class)
  private Integer queryType;
}
