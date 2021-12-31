package com.lframework.starter.security.event;

import org.springframework.context.ApplicationEvent;

/**
 * 用户登录事件
 *
 * @author zmj
 */
public class LoginEvent extends ApplicationEvent {

    /**
     * Create a new {@code ApplicationEvent}.
     * @param source the object on which the event initially occurred or with
     * which the event is associated (never {@code null})
     */
    public LoginEvent(Object source) {

        super(source);
    }
}
