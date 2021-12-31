package com.lframework.starter.security.controller;

import cn.hutool.core.codec.Base64;
import com.google.code.kaptcha.Producer;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.redis.components.RedisHandler;
import com.lframework.starter.web.components.security.AbstractUserDetails;
import com.lframework.starter.web.config.KaptchaProperties;
import com.lframework.starter.web.dto.GenerateCaptchaDto;
import com.lframework.starter.web.dto.LoginDto;
import com.lframework.starter.web.dto.MenuDto;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.service.IMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * 默认用户认证Controller
 *
 * @author zmj
 */
@Slf4j
@Validated
@RestController
@ConditionalOnProperty(value = "default-setting.controller.enabled", matchIfMissing = true)
public class AuthController extends SecurityController {

    @Autowired
    private Producer producer;

    @Autowired
    private RedisHandler redisHandler;

    @Autowired
    private KaptchaProperties kaptchaProperties;

    @Autowired
    private IMenuService menuService;

    /**
     * 获取登录验证码
     */
    @GetMapping(StringPool.CAPTCHA_URL)
    public InvokeResult generateCaptcha() {

        String code = producer.createText();
        BufferedImage image = producer.createImage(code);

        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            throw new DefaultClientException("验证码生成失败，请稍后重试！");
        }

        String sn = IdUtil.getId();
        //将验证码存至redis
        redisHandler.set(StringUtil.format(StringPool.LOGIN_CAPTCHA_KEY, sn), code,
                kaptchaProperties.getExpireTime() * 60 * 1000L);

        GenerateCaptchaDto resp = new GenerateCaptchaDto();
        resp.setSn(sn);
        resp.setImage("data:image/jpeg;base64," + Base64.encode(os.toByteArray()));

        if (log.isDebugEnabled()) {
            log.debug("获取验证码成功, SN={}, code={}", sn, code);
        }

        return InvokeResultBuilder.success(resp);
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/auth/info")
    public InvokeResult info(HttpServletRequest request) {

        AbstractUserDetails user = getCurrentUser();
        LoginDto info = new LoginDto(request.getSession(false).getId(), user.getName(), user.getPermissions());

        return InvokeResultBuilder.success(info);
    }

    /**
     * 获取左侧菜单
     */
    @GetMapping("/auth/menus")
    public InvokeResult menus() {

        AbstractUserDetails user = getCurrentUser();
        List<MenuDto> menus = menuService.getMenuByUserId(user.getId(), user.isAdmin());

        return InvokeResultBuilder.success(menus);
    }

    /**
     * 收藏菜单
     */
    @PostMapping("/menu/collect")
    public InvokeResult collectMenu(String menuId) {

        AbstractUserDetails user = getCurrentUser();
        menuService.collect(user.getId(), menuId);

        return InvokeResultBuilder.success();
    }

    /**
     * 取消收藏菜单
     */
    @PostMapping("/menu/collect/cancel")
    public InvokeResult cancelCollectMenu(String menuId) {

        AbstractUserDetails user = getCurrentUser();
        menuService.cancelCollect(user.getId(), menuId);

        return InvokeResultBuilder.success();
    }
}
