package com.lframework.starter.web.inner.bo.system.notice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.inner.dto.system.notice.QuerySysNoticeByUserDto;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 我的系统通知 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
public class QueryMySysNoticeBo extends BaseBo<QuerySysNoticeByUserDto> {

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
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime publishTime;

  public QueryMySysNoticeBo() {

  }

  public QueryMySysNoticeBo(QuerySysNoticeByUserDto dto) {

    super(dto);
  }
}
