package com.lframework.starter.web.inner.bo.system.permission;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.core.annotations.convert.EnumConvert;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.inner.entity.SysDataPermissionModelDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SysDataPermissionModelDetailBo extends BaseBo<SysDataPermissionModelDetail> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private Integer id;

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
  private String[] conditionTypes;

  /**
   * 输入类型
   */
  @Schema(description = "输入类型")
  @EnumConvert
  private Integer inputType;

  /**
   * 前段枚举名
   */
  @Schema(description = "前端枚举名")
  private String enumName;

  public SysDataPermissionModelDetailBo() {
  }

  public SysDataPermissionModelDetailBo(SysDataPermissionModelDetail dto) {
    super(dto);
  }

  @Override
  protected void afterInit(SysDataPermissionModelDetail dto) {
    this.conditionTypes = dto.getConditionType().split(StringPool.STR_SPLIT);
  }
}
