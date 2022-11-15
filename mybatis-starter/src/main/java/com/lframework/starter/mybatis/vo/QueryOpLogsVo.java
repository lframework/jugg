package com.lframework.starter.mybatis.vo;

import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询操作日志Vo
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryOpLogsVo extends PageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 日志名称
   */
  @ApiModelProperty("日志名称")
  private String name;

  /**
   * 创建人ID
   */
  @ApiModelProperty("创建人ID")
  private String createById;

  /**
   * 日志类别
   */
  @ApiModelProperty("日志类别")
  @IsEnum(message = "日志类别不存在！", enumClass = OpLogType.class)
  private Integer logType;

  /**
   * 创建起始时间
   */
  @ApiModelProperty("创建起始时间")
  private LocalDateTime startTime;

  /**
   * 创建截止时间
   */
  @ApiModelProperty("创建截止时间")
  private LocalDateTime endTime;
}
