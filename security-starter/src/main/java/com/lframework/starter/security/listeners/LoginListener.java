package com.lframework.starter.security.listeners;

import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.vo.CreateOpLogsVo;
import com.lframework.starter.security.event.LoginEvent;
import com.lframework.starter.web.components.security.AbstractUserDetails;
import com.lframework.starter.web.utils.SecurityUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 用户登录监听器
 *
 * @author zmj
 */
@Component
public class LoginListener implements ApplicationListener<LoginEvent> {

    @Override
    public void onApplicationEvent(LoginEvent event) {

        AbstractUserDetails currentUser = SecurityUtil.getCurrentUser();
        CreateOpLogsVo vo = new CreateOpLogsVo();
        vo.setName("用户登录");
        vo.setLogType(OpLogType.AUTH.getCode());
        vo.setCreateBy(currentUser.getId());
        vo.setIp(currentUser.getIp());

        OpLogUtil.addLog(vo);
    }
}
