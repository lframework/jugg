package com.lframework.starter.security.jwt.config;

import com.lframework.common.constants.StringPool;
import com.lframework.starter.security.components.CaptchaFilter;
import com.lframework.starter.security.components.DefaultLogoutHandler;
import com.lframework.starter.security.components.PasswordEncoderWrapper;
import com.lframework.starter.security.components.PermitAllService;
import com.lframework.starter.security.jwt.components.JwtFilter;
import java.util.List;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

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
   * 授权无权限处理
   */
  @Autowired
  private AccessDeniedHandler accessDeniedHandler;

  /**
   * 验证码校验Filter
   */
  @Autowired
  private CaptchaFilter captchaFilter;

  @Autowired
  private JwtFilter jwtFilter;

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
        .logoutUrl(StringPool.LOGOUT_API_URL).deleteCookies(StringPool.HEADER_NAME_SESSION_ID)
        .addLogoutHandler(new DefaultLogoutHandler()).logoutSuccessHandler(logoutSuccessHandler)
        .and().exceptionHandling().and().sessionManagement().disable();

    http.csrf().disable();
    http.headers().frameOptions().disable();
    http.addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);
    http.addFilterAfter(jwtFilter, FilterSecurityInterceptor.class);

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
}
