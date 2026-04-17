package com.lframework.starter.web.inner.bo.system.notice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.inner.entity.SysNotice;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 系统通知 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
public class QuerySysNoticeBo extends BaseBo<SysNotice> {

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
   * 状态
   */
  @Schema(description = "状态")
  private Boolean available;

  /**
   * 是否发布
   */
  @Schema(description = "是否发布")
  private Boolean published;

  /**
   * 发布时间
   */
  @Schema(description = "发布时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime publishTime;

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

  /**
   * 已读人数
   */
  @Schema(description = "已读人数")
  private Integer readedNum;

  /**
   * 未读人数
   */
  @Schema(description = "未读人数")
  private Integer unReadNum;

  public QuerySysNoticeBo() {

  }

  public QuerySysNoticeBo(SysNotice dto) {

    super(dto);
  }

  @Override
  protected void afterInit(SysNotice dto) {
  }
}
