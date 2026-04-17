package com.lframework.starter.web.inner.bo.system.dept;

import com.lframework.starter.web.inner.entity.SysDept;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GetSysDeptBo extends BaseBo<SysDept> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

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
   * 父级ID
   */
  @Schema(description = "父级ID")
  private String parentId;

  /**
   * 简称
   */
  @Schema(description = "简称")
  private String shortName;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  public GetSysDeptBo() {

  }

  public GetSysDeptBo(SysDept dto) {

    super(dto);
  }
}
