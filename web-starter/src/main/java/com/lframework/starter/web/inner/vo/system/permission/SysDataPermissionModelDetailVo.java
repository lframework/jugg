package com.lframework.starter.web.inner.vo.system.permission;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SysDataPermissionModelDetailVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @Schema(description = "ID")

  private String id;

  /**
   * 明细ID
   */
  @Schema(description = "明细ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "明细ID不能为空！")
  private Integer detailId;

  /**
   * 节点类型
   */
  @Schema(description = "节点类型", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "节点类型不能为空！")
  private Integer nodeType;

  /**
   * 计算类型
   */
  @Schema(description = "计算类型")
  private Integer calcType;

  /**
   * 值
   */
  @Schema(description = "值")
  private String value;

  /**
   * 值
   */
  @Schema(description = "值")
  private List<String> values;

  /**
   * 条件类型
   */
  @Schema(description = "条件类型")
  private Integer conditionType;

  /**
   * 子节点
   */
  @Schema(description = "子节点")
  private List<SysDataPermissionModelDetailVo> children;
}
