package com.lframework.starter.security.listeners;

import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.vo.CreateOpLogsVo;
import com.lframework.starter.security.event.LogoutEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 用户退出登录监听器
 *
 * @author zmj
 */
@Component
public class LogoutListener implements ApplicationListener<LogoutEvent> {

  @Override
  public void onApplicationEvent(LogoutEvent event) {

    CreateOpLogsVo vo = new CreateOpLogsVo();
    vo.setName("退出登录");
    vo.setLogType(OpLogType.AUTH.getCode());
    vo.setCreateBy(event.getUser().getId());
    vo.setIp(event.getUser().getIp());

    OpLogUtil.addLog(vo);
  }
}
