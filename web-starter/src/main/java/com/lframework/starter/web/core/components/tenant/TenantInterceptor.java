package com.lframework.starter.web.core.components.tenant;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 租户拦截器，要在LoginInterceptor后拦截
 *
 * @author zmj
 */
public interface TenantInterceptor extends HandlerInterceptor {

}
