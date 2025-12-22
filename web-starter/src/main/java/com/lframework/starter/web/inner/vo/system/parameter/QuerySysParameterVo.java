package com.lframework.starter.web.inner.vo.system.parameter;

import com.lframework.starter.web.core.components.validation.TypeMismatch;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.core.vo.SortPageVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuerySysParameterVo extends SortPageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 租户ID
   */
  @ApiModelProperty(value = "租户ID", required = true)
  @NotNull(message = "租户ID不能为空！")
  private Integer tenantId;

  /**
   * 键
   */
  @ApiModelProperty("键")
  private String pmKey;

  /**
   * 创建时间 起始时间
   */
  @ApiModelProperty("创建时间 起始时间")
  @TypeMismatch(message = "创建时间起始时间格式有误！")
  private LocalDateTime createTimeStart;

  /**
   * 创建时间 截止时间
   */
  @ApiModelProperty("创建时间 截止时间")
  @TypeMismatch(message = "创建时间截止时间格式有误！")
  private LocalDateTime createTimeEnd;

}
