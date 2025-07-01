package com.lframework.starter.web.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.web.inner.mappers.system.SysNoticeLogMapper;
import com.lframework.starter.web.inner.entity.SysNoticeLog;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.inner.service.system.SysNoticeLogService;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysNoticeLogServiceImpl extends
    BaseMpServiceImpl<SysNoticeLogMapper, SysNoticeLog> implements SysNoticeLogService {

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean setReaded(String noticeId, String userId) {
    Wrapper<SysNoticeLog> updateWrapper = Wrappers.lambdaUpdate(SysNoticeLog.class)
        .eq(SysNoticeLog::getNoticeId, noticeId).eq(SysNoticeLog::getUserId, userId)
        .eq(SysNoticeLog::getReaded, Boolean.FALSE).set(SysNoticeLog::getReaded, Boolean.TRUE)
        .set(SysNoticeLog::getReadTime,
            LocalDateTime.now());

    return this.update(updateWrapper);
  }
}
