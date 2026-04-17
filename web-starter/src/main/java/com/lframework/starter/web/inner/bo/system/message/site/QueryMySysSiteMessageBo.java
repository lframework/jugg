package com.lframework.starter.web.inner.bo.system.message.site;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.inner.entity.SysSiteMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 我的站内信 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
public class QueryMySysSiteMessageBo extends BaseBo<SysSiteMessage> {

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
   * 创建时间
   */
  @Schema(description = "创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  public QueryMySysSiteMessageBo() {

  }

  public QueryMySysSiteMessageBo(SysSiteMessage dto) {

    super(dto);
  }
}
