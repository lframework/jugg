package com.lframework.gen.vo.dataobj;

import com.lframework.gen.enums.GenQueryType;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateQueryParamsColumnConfigVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @NotNull(message = "ID不能为空！")
  private String id;

  /**
   * 查询类型
   */
  @NotNull(message = "查询类型不能为空！")
  @IsEnum(message = "查询类型不能为空！", enumClass = GenQueryType.class)
  private Integer queryType;
}
