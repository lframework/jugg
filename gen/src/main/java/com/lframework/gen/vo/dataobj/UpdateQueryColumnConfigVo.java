package com.lframework.gen.vo.dataobj;

import com.lframework.gen.enums.GenQueryWidthType;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateQueryColumnConfigVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @NotNull(message = "ID不能为空！")
  private String id;

  /**
   * 宽度类型
   */
  @NotNull(message = "宽度类型不能为空！")
  @IsEnum(message = "宽度类型不能为空！", enumClass = GenQueryWidthType.class)
  private Integer widthType;

  /**
   * 是否页面排序
   */
  @NotNull(message = "请选择是否页面排序！")
  private Boolean sortable;

  /**
   * 宽度
   */
  @NotNull(message = "宽度不能为空！")
  @Min(value = 1, message = "宽度必须大于0！")
  private Integer width;
}
