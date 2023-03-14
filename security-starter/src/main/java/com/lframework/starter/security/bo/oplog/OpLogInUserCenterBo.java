package com.lframework.starter.security.bo.oplog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.mybatis.entity.DefaultOpLogs;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OpLogInUserCenterBo extends BaseBo<DefaultOpLogs> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 日志名称
   */
  @ApiModelProperty("日志名称")
  private String name;

  /**
   * 类别
   */
  @ApiModelProperty("类别")
  private Integer logType;

  /**
   * IP
   */
  @ApiModelProperty("IP")
  private String ip;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  public OpLogInUserCenterBo() {

  }

  public OpLogInUserCenterBo(DefaultOpLogs dto) {

    super(dto);
  }
}
