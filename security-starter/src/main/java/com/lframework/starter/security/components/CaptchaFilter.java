package com.lframework.starter.security.components;

import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.redis.components.RedisHandler;
import com.lframework.starter.security.dto.system.config.SysConfigDto;
import com.lframework.starter.security.service.system.ISysConfigService;
import com.lframework.starter.web.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码Filter
 *
 * @author zmj
 */
@Component
@ConditionalOnProperty(value = "default-setting.security.enabled", matchIfMissing = true)
public class CaptchaFilter extends OncePerRequestFilter {

    private AntPathRequestMatcher matcher = new AntPathRequestMatcher(StringPool.LOGIN_API_URL, "POST");

    @Autowired
    private RedisHandler redisHandler;

    @Autowired
    private ISysConfigService sysConfigService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (!matcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        SysConfigDto config = sysConfigService.get();
        if (!config.getAllowCaptcha()) {
            filterChain.doFilter(request, response);
            return;
        }

        String sn = request.getParameter(StringPool.SN_PARAMETER_NAME);
        String code = request.getParameter(StringPool.CAPTCHA_PARAMETER_NAME);

        if (StringUtil.isEmpty(sn) || StringUtil.isEmpty(code)) {
            ResponseUtil.respFailJson(response, new DefaultClientException("验证码错误，请重新输入！"));
            return;
        }

        String captchaKey = StringUtil.format(StringPool.LOGIN_CAPTCHA_KEY, sn);
        try {
            String captcha = (String) redisHandler.get(captchaKey);
            if (StringUtil.isEmpty(captcha)) {
                ResponseUtil.respFailJson(response, new DefaultClientException("验证码已过期，请重新输入！"));
                return;
            }
            if (!StringUtil.equalsIgnoreCase(captcha, code)) {
                ResponseUtil.respFailJson(response, new DefaultClientException("验证码错误，请重新输入！"));
                return;
            }
        } finally {
            redisHandler.del(captchaKey);
        }

        filterChain.doFilter(request, response);
    }
}
