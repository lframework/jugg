package com.lframework.starter.mybatis.dto.message;

import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.mybatis.dto.system.notice.QuerySysNoticeByUserDto;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class SysNoticeTaskDto extends BaseBo<QuerySysNoticeByUserDto> implements MessageBusDto,
    Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 标题
   */
  @ApiModelProperty("标题")
  private String title;

  /**
   * 是否已读
   */
  @ApiModelProperty("是否已读")
  private Boolean readed;

  /**
   * 发布时间
   */
  @ApiModelProperty("发布时间")
  private String publishTime;

  public SysNoticeTaskDto(QuerySysNoticeByUserDto dto) {
    super(dto);
  }

  @Override
  protected void afterInit(QuerySysNoticeByUserDto dto) {
    this.publishTime = DateUtil.formatDateTime(dto.getPublishTime());
  }
}
