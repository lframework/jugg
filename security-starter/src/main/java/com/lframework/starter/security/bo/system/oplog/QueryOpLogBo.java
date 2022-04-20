package com.lframework.starter.security.bo.system.oplog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.mybatis.dto.DefaultOpLogsDto;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryOpLogBo extends BaseBo<DefaultOpLogsDto> {

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
   * IP地址
   */
  @ApiModelProperty("IP地址")
  private String ip;

  /**
   * 创建人
   */
  @ApiModelProperty("创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  public QueryOpLogBo() {

  }

  public QueryOpLogBo(DefaultOpLogsDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<DefaultOpLogsDto> convert(DefaultOpLogsDto dto) {

    return super.convert(dto, QueryOpLogBo::getLogType);
  }

  @Override
  protected void afterInit(DefaultOpLogsDto dto) {

    this.logType = dto.getLogType().getCode();

    IUserService userService = ApplicationUtil.getBean(IUserService.class);
    UserDto createBy = userService.findById(dto.getCreateBy());
    this.createBy = createBy.getName();
  }
}
