package com.lframework.starter.security.listeners;

import com.lframework.starter.mybatis.enums.DefaultOpLogType;
import com.lframework.starter.mybatis.events.LogoutEvent;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.vo.CreateOpLogsVo;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class LogoutListener implements ApplicationListener<LogoutEvent> {

  @Override
  public void onApplicationEvent(LogoutEvent event) {

    CreateOpLogsVo vo = new CreateOpLogsVo();
    vo.setName("退出登录");
    vo.setLogType(DefaultOpLogType.AUTH);
    vo.setCreateBy(event.getUser().getName());
    vo.setCreateById(event.getUser().getId());
    vo.setIp(event.getUser().getIp());

    OpLogUtil.addLog(vo);
  }
}
