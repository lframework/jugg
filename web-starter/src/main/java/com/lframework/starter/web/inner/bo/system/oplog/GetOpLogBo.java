package com.lframework.starter.web.inner.bo.system.oplog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.inner.entity.OpLogs;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class GetOpLogBo extends BaseBo<OpLogs> {

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
   * IP地址
   */
  @Schema(description = "IP地址")
  private String ip;

  /**
   * 补充信息
   */
  @Schema(description = "补充信息")
  private String extra;

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

  public GetOpLogBo() {

  }

  public GetOpLogBo(OpLogs dto) {

    super(dto);
  }
}
