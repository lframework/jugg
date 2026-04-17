package com.lframework.starter.web.inner.bo.system.dept;

import com.lframework.starter.web.inner.entity.SysDept;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SysDeptTreeBo extends BaseBo<SysDept> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

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

  public SysDeptTreeBo() {

  }

  public SysDeptTreeBo(SysDept dto) {

    super(dto);
  }
}
