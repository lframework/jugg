package com.lframework.starter.security.listeners;

import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.events.LoginEvent;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.vo.CreateOpLogsVo;
import com.lframework.web.common.security.AbstractUserDetails;
import com.lframework.web.common.security.SecurityUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class LoginListener implements ApplicationListener<LoginEvent> {

  @Override
  public void onApplicationEvent(LoginEvent loginEvent) {
    AbstractUserDetails currentUser = SecurityUtil.getCurrentUser();
    CreateOpLogsVo vo = new CreateOpLogsVo();
    vo.setName("用户登录");
    vo.setLogType(OpLogType.AUTH.getCode());
    vo.setCreateBy(currentUser.getId());
    vo.setIp(currentUser.getIp());

    OpLogUtil.addLog(vo);
  }
}
