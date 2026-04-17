package com.lframework.starter.web.inner.vo.system.dic.item;

import com.lframework.starter.web.core.vo.SortPageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuerySysDataDicItemVo extends SortPageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 租户ID
   */
  @Schema(description = "租户ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "租户ID不能为空！")
  private Integer tenantId;

  /**
   * 编号
   */
  @Schema(description = "编号")
  private String code;

  /**
   * 名称
   */
  @Schema(description = "名称")
  private String name;

  /**
   * 字典ID
   */
  @Schema(description = "字典ID")
  @NotBlank(message = "字典ID不能为空！")
  private String dicId;
}
