package com.lframework.starter.web.inner.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 系统通知记录
 * </p>
 *
 * @author zmj
 */
@Data
@TableName("sys_notice_log")
public class SysNoticeLog extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "SysNoticeLog";
  private static final long serialVersionUID = 1L;
  /**
   * ID
   */
  private String id;

  /**
   * 标题
   */
  private String noticeId;

  /**
   * 用户ID
   */
  private String userId;

  /**
   * 是否已读
   */
  private Boolean readed;

  /**
   * 已读时间
   */
  private LocalDateTime readTime;

}
