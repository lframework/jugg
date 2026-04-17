package com.lframework.starter.web.inner.vo.oplogs;

import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 查询操作日志Vo
 *
 * @author zmj
 */
@Data
public class QueryOpLogsVo extends PageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 日志名称
   */
  @Schema(description = "日志名称")
  private String name;

  /**
   * 创建人ID
   */
  @Schema(description = "创建人ID")
  private String createBy;

  /**
   * 日志类别
   */
  @Schema(description = "日志类别")
  private Integer logType;

  /**
   * 创建起始时间
   */
  @Schema(description = "创建起始时间")
  private LocalDateTime startTime;

  /**
   * 创建截止时间
   */
  @Schema(description = "创建截止时间")
  private LocalDateTime endTime;
}
