package com.lframework.starter.web.inner.bo.system.parameter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.inner.entity.SysParameter;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 系统参数 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
public class QuerySysParameterBo extends BaseBo<SysParameter> {

  /**
   * ID
   */
  @JsonSerialize(using = ToStringSerializer.class)
  @Schema(description = "ID")
  private Long id;

  /**
   * 键
   */
  @Schema(description = "键")
  private String pmKey;

  /**
   * 值
   */
  @Schema(description = "值")
  private String pmValue;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  /**
   * 创建人
   */
  @Schema(description = "创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @Schema(description = "创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  public QuerySysParameterBo() {

  }

  public QuerySysParameterBo(SysParameter dto) {

    super(dto);
  }

  @Override
  protected void afterInit(SysParameter dto) {
  }
}
