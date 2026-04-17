package com.lframework.starter.web.inner.dto.message;

import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.inner.dto.system.notice.QuerySysNoticeByUserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

@Data
public class SysNoticeTaskDto extends BaseBo<QuerySysNoticeByUserDto> implements BaseDto,
    Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 标题
   */
  @Schema(description = "标题")
  private String title;

  /**
   * 是否已读
   */
  @Schema(description = "是否已读")
  private Boolean readed;

  /**
   * 发布时间
   */
  @Schema(description = "发布时间")
  private String publishTime;

  public SysNoticeTaskDto(QuerySysNoticeByUserDto dto) {
    super(dto);
  }

  @Override
  protected void afterInit(QuerySysNoticeByUserDto dto) {
    this.publishTime = DateUtil.formatDateTime(dto.getPublishTime());
  }
}
