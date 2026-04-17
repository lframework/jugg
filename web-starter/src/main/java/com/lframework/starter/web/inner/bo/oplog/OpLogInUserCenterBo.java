package com.lframework.starter.web.inner.bo.oplog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.inner.entity.OpLogs;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class OpLogInUserCenterBo extends BaseBo<OpLogs> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 日志名称
   */
  @Schema(description = "日志名称")
  private String name;

  /**
   * 类别
   */
  @Schema(description = "类别")
  private Integer logType;

  /**
   * IP
   */
  @Schema(description = "IP")
  private String ip;

  /**
   * 创建时间
   */
  @Schema(description = "创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  public OpLogInUserCenterBo() {

  }

  public OpLogInUserCenterBo(OpLogs dto) {

    super(dto);
  }
}
