package com.lframework.starter.web.inner.bo.system.generate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.inner.entity.SysGenerateCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 编号规则 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
public class QuerySysGenerateCodeBo extends BaseBo<SysGenerateCode> {

  /**
   * ID
   */
  @JsonSerialize(using = ToStringSerializer.class)
  @Schema(description = "ID")
  private Long id;

  /**
   * 名称
   */
  @Schema(description = "名称")
  private String name;

  public QuerySysGenerateCodeBo() {

  }

  public QuerySysGenerateCodeBo(SysGenerateCode dto) {

    super(dto);
  }

  @Override
  protected void afterInit(SysGenerateCode dto) {
  }
}
