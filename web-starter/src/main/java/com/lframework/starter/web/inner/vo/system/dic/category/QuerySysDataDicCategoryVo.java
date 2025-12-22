package com.lframework.starter.web.inner.vo.system.dic.category;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuerySysDataDicCategoryVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 租户ID
   */
  @ApiModelProperty(value = "租户ID", required = true)
  @NotNull(message = "租户ID不能为空！")
  private Integer tenantId;
}
