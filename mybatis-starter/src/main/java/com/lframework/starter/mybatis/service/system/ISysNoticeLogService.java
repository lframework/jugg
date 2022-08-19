package com.lframework.starter.mybatis.service.system;

import com.lframework.starter.mybatis.entity.SysNoticeLog;
import com.lframework.starter.mybatis.service.BaseMpService;

/**
 * 系统通知记录 Service
 *
 * @author zmj
 */
public interface ISysNoticeLogService extends BaseMpService<SysNoticeLog> {

  /**
   * 设置已读
   *
   * @param noticeId
   * @param userId
   */
  boolean setReaded(String noticeId, String userId);
}
