package com.lframework.starter.web.inner.vo.system.dic.item;

import com.lframework.starter.web.core.vo.SortPageVo;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QuerySysDataDicItemVo extends SortPageVo {

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
   * 字典ID
   */
  @ApiModelProperty("字典ID")
  @NotBlank(message = "字典ID不能为空！")
  private String dicId;
}
