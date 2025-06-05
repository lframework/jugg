package com.lframework.starter.web.inner.service.system;

import com.lframework.starter.web.inner.entity.SysNoticeLog;
import com.lframework.starter.web.core.service.BaseMpService;

/**
 * 系统通知记录 Service
 *
 * @author zmj
 */
public interface SysNoticeLogService extends BaseMpService<SysNoticeLog> {

  /**
   * 设置已读
   *
   * @param noticeId
   * @param userId
   */
  boolean setReaded(String noticeId, String userId);
}
