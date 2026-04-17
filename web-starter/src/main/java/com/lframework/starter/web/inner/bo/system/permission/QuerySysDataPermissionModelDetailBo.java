package com.lframework.starter.web.inner.bo.system.permission;

import com.lframework.starter.web.core.annotations.convert.EnumConvert;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.dto.VoidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
public class QuerySysDataPermissionModelDetailBo extends BaseBo<VoidDto> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 明细ID
   */
  @Schema(description = "明细ID")
  private Integer detailId;

  /**
   * 节点类型
   */
  @Schema(description = "节点类型")
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
   * 名称
   */
  @Schema(description = "名称")
  private String name;

  /**
   * 模型ID
   */
  @Schema(description = "模型ID")
  private Integer modelId;

  /**
   * 条件
   */
  @Schema(description = "条件")
  private Integer[] conditionTypes;

  /**
   * 输入类型
   */
  @Schema(description = "输入类型")
  @EnumConvert
  private Integer inputType;

  /**
   * 条件类型
   */
  @Schema(description = "条件类型")
  private Integer conditionType;

  /**
   * 前段枚举名
   */
  @Schema(description = "前端枚举名")
  private String enumName;

  /**
   * 子节点
   */
  @Schema(description = "子节点")
  private List<QuerySysDataPermissionModelDetailBo> children;

  public QuerySysDataPermissionModelDetailBo() {
  }
}
