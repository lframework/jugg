package com.lframework.starter.web.inner.bo.system.module;

import com.lframework.starter.web.inner.entity.SysModule;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QuerySysModuleBo extends BaseBo<SysModule> {

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
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  /**
   * 是否启用
   */
  @Schema(description = "是否启用")
  private Boolean enabled;

  /**
   * 过期时间
   */
  @Schema(description = "过期时间")
  private LocalDateTime expireTime;

  public QuerySysModuleBo(SysModule dto) {
    super(dto);
  }
}
