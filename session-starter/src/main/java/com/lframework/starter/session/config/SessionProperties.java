package com.lframework.starter.session.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Session配置信息
 *
 * @author zmj
 */
@ConfigurationProperties(prefix = "session")
public class SessionProperties {

    /**
     * 同一个用户最多同时在线数量
     */
    private Integer maximumSessions = 1;

    /**
     * 达到最多用户数后是否保持在线
     */
    private Boolean maxSessionsPreventsLogin = false;

    public Integer getMaximumSessions() {

        return maximumSessions;
    }

    public void setMaximumSessions(Integer maximumSessions) {

        this.maximumSessions = maximumSessions;
    }

    public Boolean getMaxSessionsPreventsLogin() {

        return maxSessionsPreventsLogin;
    }

    public void setMaxSessionsPreventsLogin(Boolean maxSessionsPreventsLogin) {

        this.maxSessionsPreventsLogin = maxSessionsPreventsLogin;
    }
}
