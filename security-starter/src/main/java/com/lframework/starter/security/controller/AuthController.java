package com.lframework.starter.security.controller;

import cn.hutool.core.codec.Base64;
import com.google.code.kaptcha.Producer;
import com.lframework.common.constants.PatternPool;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.RegUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.common.utils.ThreadUtil;
import com.lframework.starter.redis.components.RedisHandler;
import com.lframework.starter.security.bo.system.config.AuthInitBo;
import com.lframework.starter.security.components.AbstractUserDetailsService;
import com.lframework.starter.security.dto.system.config.SysConfigDto;
import com.lframework.starter.security.service.system.ISysConfigService;
import com.lframework.starter.security.service.system.ISysUserService;
import com.lframework.starter.security.vo.system.user.RegistUserVo;
import com.lframework.starter.web.components.security.AbstractUserDetails;
import com.lframework.starter.web.components.validation.Pattern;
import com.lframework.starter.web.config.KaptchaProperties;
import com.lframework.starter.web.dto.GenerateCaptchaDto;
import com.lframework.starter.web.dto.LoginDto;
import com.lframework.starter.web.dto.MenuDto;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.service.IAliSmsService;
import com.lframework.starter.web.service.IMailService;
import com.lframework.starter.web.service.IMenuService;
import com.lframework.starter.web.service.IUserService;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @Autowired
  private ISysConfigService sysConfigService;

  @Autowired
  private ISysUserService sysUserService;

  @Autowired
  private AbstractUserDetailsService userDetailsService;

  @Autowired
  private IUserService userService;

  @Autowired
  private IMailService mailService;

  @Autowired
  private IAliSmsService aliSmsService;

  /**
   * 登录初始化参数
   *
   * @return
   */
  @GetMapping(StringPool.AUTH_INIT_URL)
  public InvokeResult getInit() {
    SysConfigDto data = sysConfigService.get();

    return InvokeResultBuilder.success(new AuthInitBo(data));
  }

  /**
   * 注册
   *
   * @param vo
   * @return
   */
  @PostMapping(StringPool.AUTH_REGIST_URL)
  public InvokeResult regist(@Valid RegistUserVo vo) {
    SysConfigDto config = sysConfigService.get();
    if (!config.getAllowRegist()) {
      throw new DefaultClientException("系统不允许注册账户！");
    }

    sysUserService.regist(vo);

    return InvokeResultBuilder.success();
  }

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
    LoginDto info = new LoginDto(null, user.getName(),
        user.getPermissions());

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

  /**
   * 忘记密码时，根据用户名获取信息
   *
   * @param username
   * @return
   */
  @GetMapping("/auth/forget/username")
  public InvokeResult getByForget(@NotBlank(message = "用户名不能为空！") String username) {

    SysConfigDto sysConfig = sysConfigService.get();
    if (!sysConfig.getAllowForgetPsw()) {
      throw new DefaultClientException("系统不允许重置密码！");
    }

    AbstractUserDetails user = userDetailsService.findByUsername(username);

    if (user == null) {
      throw new DefaultClientException("用户名不存在！");
    }

    Map<String, String> results = new HashMap<>(2, 1);

    results.put("username", user.getUsername());
    results.put("email", StringUtil.encodeEmail(user.getEmail()));
    results.put("telephone", StringUtil.encodeTelephone(user.getTelephone()));

    return InvokeResultBuilder.success(results);
  }

  /**
   * 发送邮箱验证码
   *
   * @return
   */
  @GetMapping("/auth/forget/mail/code")
  public InvokeResult sendMailCodeByForget(@NotBlank(message = "用户名不能为空！") String username) {

    SysConfigDto sysConfig = sysConfigService.get();
    if (!sysConfig.getAllowForgetPsw()) {
      throw new DefaultClientException("系统不允许重置密码！");
    }

    AbstractUserDetails user = userDetailsService.findByUsername(username);

    if (user == null) {
      throw new DefaultClientException("用户名不存在！");
    }

    if (!RegUtil.isMatch(PatternPool.EMAIL, user.getEmail())) {
      throw new DefaultClientException("用户邮箱不存在，无法重置密码！");
    }

    String key = "FORGET_PSW_MAIL_CODE_" + username;
    String checkKey = "FORGET_PSW_CHECK_MAIL_CODE_" + username;
    if (redisHandler.get(checkKey) != null) {
      throw new DefaultClientException("获取验证码过于频繁，请稍后再试！");
    } else {
      redisHandler.set(checkKey, true, 60 * 1000L);
    }

    String code = (String) redisHandler.get(key);
    if (code == null) {
      code = producer.createText();
      redisHandler.set(key, code, 15 * 60 * 1000L);
    }

    String content = "您正在重置密码，验证码【" + code + "】，切勿将验证码泄露于他人，本条验证码有效期15分钟。";

    ThreadUtil.execAsync(() -> {
      mailService.send(user.getEmail(), "重置密码", content);
    });

    return InvokeResultBuilder.success();
  }

  /**
   * 根据邮箱验证码重置密码
   *
   * @param username
   * @param password
   * @param captcha
   * @return
   */
  @PostMapping("/auth/forget/mail")
  public InvokeResult updatePswByMail(@NotBlank(message = "用户名不能为空！") String username,
      @NotBlank(message = "新密码不能为空！") @Pattern(regexp = PatternPool.PATTERN_STR_PASSWORD, message = "密码长度必须为5-16位，只允许包含大写字母、小写字母、数字、下划线") String password,
      @NotBlank(message = "验证码不能为空！") String captcha) {

    SysConfigDto sysConfig = sysConfigService.get();
    if (!sysConfig.getAllowForgetPsw()) {
      throw new DefaultClientException("系统不允许重置密码！");
    }

    AbstractUserDetails user = userDetailsService.findByUsername(username);

    if (user == null) {
      throw new DefaultClientException("用户名不存在！");
    }

    String key = "FORGET_PSW_MAIL_CODE_" + username;
    String codeInDb = (String) redisHandler.get(key);
    if (codeInDb == null) {
      throw new DefaultClientException("验证码已过期，请重新获取！");
    }

    if (!captcha.equalsIgnoreCase(codeInDb)) {
      throw new DefaultClientException("验证码不正确，请重新输入！");
    }

    redisHandler.del(key);

    userService.updatePassword(user.getId(), password);

    return InvokeResultBuilder.success();
  }

  /**
   * 发送短信验证码
   *
   * @return
   */
  @GetMapping("/auth/forget/sms/code")
  public InvokeResult sendSmsCodeByForget(@NotBlank(message = "用户名不能为空！") String username) {

    SysConfigDto sysConfig = sysConfigService.get();
    if (!sysConfig.getAllowForgetPsw()) {
      throw new DefaultClientException("系统不允许重置密码！");
    }

    AbstractUserDetails user = userDetailsService.findByUsername(username);

    if (user == null) {
      throw new DefaultClientException("用户名不存在！");
    }

    if (!RegUtil.isMatch(PatternPool.PATTERN_CN_TEL, user.getTelephone())) {
      throw new DefaultClientException("用户联系电话不存在，无法重置密码！");
    }

    String key = "FORGET_PSW_SMS_CODE_" + username;
    String checkKey = "FORGET_PSW_CHECK_SMS_CODE_" + username;
    if (redisHandler.get(checkKey) != null) {
      throw new DefaultClientException("获取验证码过于频繁，请稍后再试！");
    } else {
      redisHandler.set(checkKey, true, 60 * 1000L);
    }

    String code = (String) redisHandler.get(key);
    if (code == null) {
      code = producer.createText();
      redisHandler.set(key, code, 15 * 60 * 1000L);
    }

    String captcha = code;

    ThreadUtil.execAsync(() -> {
      aliSmsService.send(user.getTelephone(), sysConfig.getSignName(), sysConfig.getTemplateCode(),
          Collections.singletonMap("code", captcha));
    });

    return InvokeResultBuilder.success();
  }

  /**
   * 根据短信验证码重置密码
   *
   * @param username
   * @param password
   * @param captcha
   * @return
   */
  @PostMapping("/auth/forget/sms")
  public InvokeResult updatePswBySms(@NotBlank(message = "用户名不能为空！") String username,
      @NotBlank(message = "新密码不能为空！") @Pattern(regexp = PatternPool.PATTERN_STR_PASSWORD, message = "密码长度必须为5-16位，只允许包含大写字母、小写字母、数字、下划线") String password,
      @NotBlank(message = "验证码不能为空！") String captcha) {

    SysConfigDto sysConfig = sysConfigService.get();
    if (!sysConfig.getAllowForgetPsw()) {
      throw new DefaultClientException("系统不允许重置密码！");
    }

    AbstractUserDetails user = userDetailsService.findByUsername(username);

    if (user == null) {
      throw new DefaultClientException("用户名不存在！");
    }

    String key = "FORGET_PSW_SMS_CODE_" + username;
    String codeInDb = (String) redisHandler.get(key);
    if (codeInDb == null) {
      throw new DefaultClientException("验证码已过期，请重新获取！");
    }

    if (!captcha.equalsIgnoreCase(codeInDb)) {
      throw new DefaultClientException("验证码不正确，请重新输入！");
    }

    redisHandler.del(key);

    userService.updatePassword(user.getId(), password);

    return InvokeResultBuilder.success();
  }
}
