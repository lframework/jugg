package com.lframework.starter.security.session.config;

import com.lframework.common.constants.StringPool;
import com.lframework.starter.security.components.CaptchaFilter;
import com.lframework.starter.security.components.DefaultLogoutHandler;
import com.lframework.starter.security.components.PasswordEncoderWrapper;
import com.lframework.starter.security.components.PermitAllService;
import com.lframework.starter.security.config.SessionProperties;
import java.util.List;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

/**
 * Spring Security配置
 *
 * @author zmj
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
@ConditionalOnProperty(value = "default-setting.security.enabled", matchIfMissing = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  /**
   * 登录密码Encoder
   */
  @Autowired
  private PasswordEncoderWrapper encoderWrapper;

  /**
   * 用户认证Service
   */
  @Autowired
  private UserDetailsService userDetailsService;

  /**
   * 退出登录成功处理
   */
  @Autowired
  private LogoutSuccessHandler logoutSuccessHandler;

  /**
   * 登录成功处理
   */
  @Autowired
  private AuthenticationSuccessHandler authenticationSuccessHandler;

  /**
   * 登陆失败处理
   */
  @Autowired
  private AuthenticationFailureHandler authenticationFailureHandler;

  /**
   * 认证无权限处理
   */
  @Autowired
  private AuthenticationEntryPoint authenticationEntryPoint;

  /**
   * Server配置信息
   */
  @Autowired
  private ServerProperties serverProperties;

  /**
   * Session配置信息
   */
  @Autowired
  private SessionProperties sessionProperties;

  /**
   * Session注册器
   */
  @Autowired
  private SessionRegistry sessionRegistry;

  /**
   * 授权无权限处理
   */
  @Autowired
  private AccessDeniedHandler accessDeniedHandler;

  /**
   * 验证码校验Filter
   */
  @Autowired
  private CaptchaFilter captchaFilter;

  /**
   * 无需权限验证的url
   */
  @Autowired
  private PermitAllService permitAllService;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth.userDetailsService(userDetailsService).passwordEncoder(encoderWrapper.getEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.cors().and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
        .accessDeniedHandler(accessDeniedHandler).and().formLogin()
        .loginProcessingUrl(StringPool.LOGIN_API_URL).successHandler(authenticationSuccessHandler)
        .failureHandler(authenticationFailureHandler).and().logout()
        .logoutUrl(StringPool.LOGOUT_API_URL)
        .deleteCookies(StringPool.HEADER_NAME_SESSION_ID)
        .addLogoutHandler(new DefaultLogoutHandler()).logoutSuccessHandler(logoutSuccessHandler)
        .and().exceptionHandling().and().sessionManagement().sessionFixation().migrateSession()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        .maximumSessions(sessionProperties.getMaximumSessions())
        .maxSessionsPreventsLogin(sessionProperties.getMaxSessionsPreventsLogin())
        .sessionRegistry(sessionRegistry);

    http.csrf().disable();
    http.headers().frameOptions().disable();
    http.addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);

    List<Entry<HttpMethod, String>> permitAllUrls = permitAllService.getUrls();
    for (Entry<HttpMethod, String> permitAllUrl : permitAllUrls) {
      http.authorizeRequests().antMatchers(permitAllUrl.getKey(), permitAllUrl.getValue())
          .permitAll();
    }
    http.authorizeRequests().anyRequest().authenticated();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {

    return super.authenticationManagerBean();
  }

  /**
   * 基于SpringSession的Registry，如果使用默认的SessionRegistryImpl 那么SpringSecurity所管理的Session并不是SpringSession提供的
   * 会导致最大用户数的限制不生效
   *
   * @param sessionRepository
   * @return
   */
  @Bean
  public SessionRegistry getSessionRegistry(RedisIndexedSessionRepository sessionRepository) {

    return new SpringSessionBackedSessionRegistry(sessionRepository);
  }
}
