package com.lframework.starter.mybatis.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.mybatis.entity.SysNoticeLog;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.mappers.system.SysNoticeLogMapper;
import com.lframework.starter.mybatis.service.system.ISysNoticeLogService;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysNoticeLogServiceImpl extends
    BaseMpServiceImpl<SysNoticeLogMapper, SysNoticeLog> implements ISysNoticeLogService {

  @Transactional
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
