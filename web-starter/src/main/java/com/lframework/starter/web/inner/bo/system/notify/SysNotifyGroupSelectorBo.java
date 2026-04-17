package com.lframework.starter.web.inner.bo.system.notify;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.inner.entity.SysNotifyGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SysNotifyGroupSelectorBo extends BaseBo<SysNotifyGroup> {

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

  public SysNotifyGroupSelectorBo() {

  }

  public SysNotifyGroupSelectorBo(SysNotifyGroup dto) {

    super(dto);
  }
}
