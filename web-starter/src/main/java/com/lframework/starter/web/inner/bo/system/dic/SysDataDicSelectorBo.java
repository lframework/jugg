package com.lframework.starter.web.inner.bo.system.dic;

import com.lframework.starter.web.inner.entity.SysDataDic;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SysDataDicSelectorBo extends BaseBo<SysDataDic> {

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

  public SysDataDicSelectorBo() {
  }

  public SysDataDicSelectorBo(SysDataDic dto) {
    super(dto);
  }
}
