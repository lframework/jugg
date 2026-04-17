package com.lframework.starter.web.inner.vo.system.parameter;

import com.lframework.starter.web.core.components.validation.TypeMismatch;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.core.vo.SortPageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuerySysParameterVo extends SortPageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 租户ID
   */
  @Schema(description = "租户ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "租户ID不能为空！")
  private Integer tenantId;

  /**
   * 键
   */
  @Schema(description = "键")
  private String pmKey;

  /**
   * 创建时间 起始时间
   */
  @Schema(description = "创建时间 起始时间")
  @TypeMismatch(message = "创建时间起始时间格式有误！")
  private LocalDateTime createTimeStart;

  /**
   * 创建时间 截止时间
   */
  @Schema(description = "创建时间 截止时间")
  @TypeMismatch(message = "创建时间截止时间格式有误！")
  private LocalDateTime createTimeEnd;

}
