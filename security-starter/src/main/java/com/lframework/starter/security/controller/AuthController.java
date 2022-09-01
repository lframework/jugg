package com.lframework.starter.security.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.codec.Base64;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.code.kaptcha.Producer;
import com.lframework.common.constants.PatternPool;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.exceptions.impl.UserLoginException;
import com.lframework.common.utils.DateUtil;
import com.lframework.common.utils.RegUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.common.utils.ThreadUtil;
import com.lframework.starter.mybatis.components.CaptchaValidator;
import com.lframework.starter.mybatis.entity.DefaultSysUser;
import com.lframework.starter.mybatis.entity.DefaultSysUserTelephone;
import com.lframework.starter.mybatis.entity.SysConfig;
import com.lframework.starter.mybatis.events.LoginEvent;
import com.lframework.starter.mybatis.events.LogoutEvent;
import com.lframework.starter.mybatis.events.UpdateUserEvent;
import com.lframework.starter.mybatis.service.IMenuService;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.mybatis.service.system.ISysConfigService;
import com.lframework.starter.mybatis.service.system.ISysUserService;
import com.lframework.starter.mybatis.service.system.ISysUserTelephoneService;
import com.lframework.starter.mybatis.vo.system.user.GetTelephoneLoginCaptchaVo;
import com.lframework.starter.mybatis.vo.system.user.LoginVo;
import com.lframework.starter.mybatis.vo.system.user.RegistUserVo;
import com.lframework.starter.mybatis.vo.system.user.TelephoneBindUserVo;
import com.lframework.starter.mybatis.vo.system.user.TelephoneLoginVo;
import com.lframework.starter.security.bo.auth.AuthInitBo;
import com.lframework.starter.security.bo.auth.ForgetPswUserInfoBo;
import com.lframework.starter.security.bo.auth.LoginBo;
import com.lframework.starter.security.bo.auth.TelephoneLoginBo;
import com.lframework.starter.web.components.redis.RedisHandler;
import com.lframework.starter.web.components.security.IUserTokenResolver;
import com.lframework.starter.web.components.security.PasswordEncoderWrapper;
import com.lframework.starter.web.components.security.UserDetailsService;
import com.lframework.starter.web.components.validation.Pattern;
import com.lframework.starter.web.config.KaptchaProperties;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.dto.GenerateCaptchaDto;
import com.lframework.starter.web.dto.LoginDto;
import com.lframework.starter.web.dto.MenuDto;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.service.IAliSmsService;
import com.lframework.starter.web.service.IMailService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.web.common.security.AbstractUserDetails;
import com.lframework.web.common.security.SecurityConstants;
import com.lframework.web.common.security.SecurityUtil;
import com.lframework.web.common.threads.DefaultRunnable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(tags = "用户认证")
@Slf4j
@Validated
@RestController
public class AuthController extends DefaultBaseController {

  @Autowired
  private Producer producer;

  @Resource(name = "numberProducer")
  private Producer numberProducer;

  @Autowired
  private ISysUserService sysUserService;

  @Autowired
  private KaptchaProperties kaptchaProperties;

  @Autowired
  private RedisHandler redisHandler;

  @Autowired
  private CaptchaValidator captchaValidator;

  @Autowired
  private PasswordEncoderWrapper passwordEncoderWrapper;

  @Autowired
  private IMenuService menuService;

  @Autowired
  private ISysConfigService sysConfigService;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private IUserService userService;

  @Autowired
  private IMailService mailService;

  @Autowired
  private IAliSmsService aliSmsService;

  @Autowired
  private IUserTokenResolver userTokenResolver;

  @Autowired
  private ISysUserTelephoneService sysUserTelephoneService;

  @ApiOperation(value = "获取登录初始化参数", notes = "用于初始化登录页面")
  @GetMapping("/auth/init")
  public InvokeResult<AuthInitBo> getInit() {

    SysConfig data = sysConfigService.get();

    return InvokeResultBuilder.success(new AuthInitBo(data));
  }

  @ApiOperation(value = "注册")
  @PostMapping("/auth/regist")
  public InvokeResult<Void> regist(@Valid RegistUserVo vo) {

    SysConfig config = sysConfigService.get();
    if (!config.getAllowRegist()) {
      throw new DefaultClientException("系统不允许注册账户！");
    }

    sysUserService.regist(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 获取登录验证码
   */
  @ApiOperation(value = "获取登录验证码")
  @GetMapping("/auth/captcha")
  public InvokeResult<GenerateCaptchaDto> generateCaptcha() {

    String code = producer.createText();
    BufferedImage image = producer.createImage(code);

    // 转换流信息写出
    FastByteArrayOutputStream os = new FastByteArrayOutputStream();
    try {
      ImageIO.write(image, "jpg", os);
    } catch (IOException e) {
      throw new DefaultClientException("验证码生成失败，请稍后重试！");
    }

    String sn = IdUtil.getUUID();
    //将验证码存至redis
    redisHandler.set(StringUtil.format(StringPool.LOGIN_CAPTCHA_KEY, sn), code,
        kaptchaProperties.getExpireTime() * 60 * 1000L);

    GenerateCaptchaDto resp = new GenerateCaptchaDto();
    resp.setSn(sn);
    resp.setImage("data:image/jpeg;base64," + Base64.encode(os.toByteArray()));

    log.debug("获取验证码成功, SN={}, code={}", sn, code);

    return InvokeResultBuilder.success(resp);
  }

  @ApiOperation("登录")
  @PostMapping("/auth/login")
  public InvokeResult<LoginBo> login(@Valid LoginVo vo) {

    String username = vo.getUsername();
    String password = vo.getPassword();

    log.info("用户 {} 开始登录", username);

    String sn = vo.getSn();
    String captcha = vo.getCaptcha();
    captchaValidator.validate(sn, captcha);

    this.checkUserLogin(username, password);

    AbstractUserDetails user = userDetailsService.loadUserByUsername(username);

    LoginDto dto = this.doLogin(user);

    return InvokeResultBuilder.success(new LoginBo(dto));
  }

  @ApiOperation("退出登录")
  @PostMapping("/auth/logout")
  public InvokeResult<Void> logout() {

    AbstractUserDetails user = getCurrentUser();

    String token = null;
    if (user != null) {
      token = userTokenResolver.getToken();
    }

    StpUtil.logout();

    if (user != null) {
      ApplicationUtil.publishEvent(new LogoutEvent(this, user, token));
    }

    return InvokeResultBuilder.success();
  }

  @ApiOperation("手机号登录")
  @PostMapping("/auth/login/telephone")
  public InvokeResult<TelephoneLoginBo> telephoneLogin(@Valid TelephoneLoginVo vo) {

    SysConfig sysConfig = sysConfigService.get();

    if (!sysConfig.getAllowTelephoneLogin()) {
      throw new DefaultClientException("系统不允许手机号登录！");
    }

    // 校验验证码
    String key = "TELEPHONE_LOGIN_SMS_CODE_" + vo.getTelephone();
    String checkKey = "TELEPHONE_LOGIN_CHECK_SMS_CODE_" + vo.getTelephone();

    String code = (String) redisHandler.get(key);
    if (code == null) {
      throw new DefaultClientException("验证码已过期，请重新获取！");
    }

    if (!code.equalsIgnoreCase(vo.getCaptcha())) {
      throw new DefaultClientException("验证码不正确，请重新输入！");
    }

    DefaultSysUserTelephone record = sysUserTelephoneService.getOne(
        Wrappers.lambdaQuery(DefaultSysUserTelephone.class)
            .eq(DefaultSysUserTelephone::getTelephone, vo.getTelephone()));
    if (record == null) {
      // 先插入手机号记录
      record = new DefaultSysUserTelephone();
      record.setId(IdUtil.getId());
      record.setTelephone(vo.getTelephone());

      sysUserTelephoneService.save(record);
    }

    try {
      // 是否已经绑定用户
      boolean isBind = !StringUtil.isBlank(record.getUserId());
      if (isBind) {
        // 查询用户
        DefaultSysUser sysUser = userService.getById(record.getUserId());

        // 进行登录
        AbstractUserDetails user = userDetailsService.loadUserByUsername(sysUser.getUsername());
        LoginDto dto = this.doLogin(user);

        return InvokeResultBuilder.success(new TelephoneLoginBo(dto));
      } else {
        // 需要绑定用户
        TelephoneLoginBo result = new TelephoneLoginBo();
        result.setIsBind(Boolean.FALSE);

        // 手机号绑定用户仅限15分钟
        String telephoneBindCheckKey = "TELEPHONE_BIND_USER_FLAG_" + vo.getTelephone();
        // 15分钟有效期
        redisHandler.set(telephoneBindCheckKey, "1", 15 * 60 * 1000);

        return InvokeResultBuilder.success(result);
      }
    } finally {
      // 删除Key
      redisHandler.del(key, checkKey);
    }
  }

  @ApiOperation("发送手机号登录的验证码")
  @GetMapping("/auth/login/telephone/captcha")
  public InvokeResult<Void> sendTelephoneLoginCaptcha(@Valid GetTelephoneLoginCaptchaVo vo) {

    SysConfig sysConfig = sysConfigService.get();

    if (!sysConfig.getAllowTelephoneLogin()) {
      throw new DefaultClientException("系统不允许手机号登录！");
    }

    String telephone = vo.getTelephone();
    // 发送短信验证码
    String key = "TELEPHONE_LOGIN_SMS_CODE_" + telephone;
    String checkKey = "TELEPHONE_LOGIN_CHECK_SMS_CODE_" + telephone;
    if (redisHandler.get(checkKey) != null) {
      throw new DefaultClientException("获取验证码过于频繁，请稍后再试！");
    } else {
      redisHandler.set(checkKey, true, 60 * 1000L);
    }

    String code = (String) redisHandler.get(key);
    if (code == null) {
      code = numberProducer.createText();
      redisHandler.set(key, code, 15 * 60 * 1000L);
    }

    String captcha = code;

    if (log.isDebugEnabled()) {
      log.debug("手机号登录验证码={}", captcha);
    }

    ThreadUtil.execAsync(new DefaultRunnable(SecurityUtil.getCurrentUser(), () -> {
      aliSmsService.send(telephone, sysConfig.getTelephoneLoginSignName(),
          sysConfig.getTelephoneLoginTemplateCode(), Collections.singletonMap("code", captcha));
    }));

    return InvokeResultBuilder.success();
  }

  @ApiOperation("手机号绑定用户")
  @PostMapping("/auth/bind/telephone")
  public InvokeResult<LoginBo> telephoneBindUser(@Valid TelephoneBindUserVo vo) {

    SysConfig sysConfig = sysConfigService.get();

    if (!sysConfig.getAllowTelephoneLogin()) {
      throw new DefaultClientException("系统不允许手机号登录！");
    }

    String telephoneBindCheckKey = "TELEPHONE_BIND_USER_FLAG_" + vo.getTelephone();
    if (redisHandler.get(telephoneBindCheckKey) == null) {
      throw new DefaultClientException("由于您长时间未操作，无法绑定账号，请重新进行操作！");
    }

    String username = vo.getUsername();
    String password = vo.getPassword();

    this.checkUserLogin(username, password);

    try {
      AbstractUserDetails user = userDetailsService.loadUserByUsername(username);

      LoginDto dto = this.doLogin(user);

      Wrapper<DefaultSysUserTelephone> updateWrapper = Wrappers.lambdaUpdate(
              DefaultSysUserTelephone.class).set(DefaultSysUserTelephone::getUserId, user.getId())
          .eq(DefaultSysUserTelephone::getTelephone, vo.getTelephone());
      sysUserTelephoneService.update(updateWrapper);

      return InvokeResultBuilder.success(new LoginBo(dto));
    } finally {
      redisHandler.del(telephoneBindCheckKey);
    }
  }

  @ApiOperation(value = "获取用户信息")
  @GetMapping("/auth/info")
  public InvokeResult<LoginDto> info() {

    AbstractUserDetails user = getCurrentUser();
    LoginDto info = new LoginDto(null, user.getName(), user.getPermissions());

    return InvokeResultBuilder.success(info);
  }

  @ApiOperation("获取用户菜单")
  @GetMapping("/auth/menus")
  public InvokeResult<List<MenuDto>> menus() {

    AbstractUserDetails user = getCurrentUser();
    List<MenuDto> menus = menuService.getMenuByUserId(user.getId(), user.isAdmin());

    return InvokeResultBuilder.success(menus);
  }

  @ApiOperation("收藏菜单")
  @ApiImplicitParam(value = "菜单ID", name = "menuId", paramType = "query")
  @PostMapping("/menu/collect")
  public InvokeResult<Void> collectMenu(String menuId) {

    AbstractUserDetails user = getCurrentUser();
    menuService.collect(user.getId(), menuId);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("取消收藏菜单")
  @ApiImplicitParam(value = "菜单ID", name = "menuId", paramType = "query")
  @PostMapping("/menu/collect/cancel")
  public InvokeResult<Void> cancelCollectMenu(String menuId) {

    AbstractUserDetails user = getCurrentUser();
    menuService.cancelCollect(user.getId(), menuId);

    return InvokeResultBuilder.success();
  }

  @ApiOperation(value = "忘记密码时，根据用户名获取信息")
  @ApiImplicitParam(value = "用户名", name = "username", paramType = "query", required = true)
  @GetMapping("/auth/forget/username")
  public InvokeResult<ForgetPswUserInfoBo> getByForget(
      @NotBlank(message = "用户名不能为空！") String username) {

    SysConfig sysConfig = sysConfigService.get();
    if (!sysConfig.getAllowForgetPsw()) {
      throw new DefaultClientException("系统不允许重置密码！");
    }

    AbstractUserDetails user = userDetailsService.findByUsername(username);

    if (user == null) {
      throw new DefaultClientException("用户名不存在！");
    }

    ForgetPswUserInfoBo result = new ForgetPswUserInfoBo();
    result.setUsername(user.getUsername());
    result.setEmail(StringUtil.encodeEmail(user.getEmail()));
    result.setTelephone(StringUtil.encodeTelephone(user.getTelephone()));

    return InvokeResultBuilder.success(result);
  }

  @ApiOperation(value = "发送邮箱验证码", notes = "用于重置密码")
  @ApiImplicitParam(value = "用户名", name = "username", paramType = "query", required = true)
  @GetMapping("/auth/forget/mail/code")
  public InvokeResult<Void> sendMailCodeByForget(@NotBlank(message = "用户名不能为空！") String username) {

    SysConfig sysConfig = sysConfigService.get();
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
      code = numberProducer.createText();
      redisHandler.set(key, code, 15 * 60 * 1000L);
    }

    String content = "您正在重置密码，验证码【" + code + "】，切勿将验证码泄露于他人，本条验证码有效期15分钟。";

    ThreadUtil.execAsync(new DefaultRunnable(SecurityUtil.getCurrentUser(), () -> {
      mailService.send(user.getEmail(), "重置密码", content);
    }));

    return InvokeResultBuilder.success();
  }

  @ApiOperation("根据邮箱验证码重置密码")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "用户名", name = "username", paramType = "query", required = true),
      @ApiImplicitParam(value = "新密码", name = "password", paramType = "query", required = true),
      @ApiImplicitParam(value = "验证码", name = "captcha", paramType = "query", required = true)})
  @PostMapping("/auth/forget/mail")
  public InvokeResult<Void> updatePswByMail(@NotBlank(message = "用户名不能为空！") String username,
      @NotBlank(message = "新密码不能为空！") @Pattern(regexp = PatternPool.PATTERN_STR_PASSWORD, message = "密码长度必须为5-16位，只允许包含大写字母、小写字母、数字、下划线") String password,
      @NotBlank(message = "验证码不能为空！") String captcha) {

    SysConfig sysConfig = sysConfigService.get();
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

    userService.cleanCacheByKey(user.getId());

    ApplicationUtil.publishEvent(new UpdateUserEvent(this, user.getId()));

    return InvokeResultBuilder.success();
  }

  /**
   * 发送短信验证码
   *
   * @return
   */
  @ApiOperation(value = "发送短信验证码", notes = "用于重置密码")
  @ApiImplicitParam(value = "用户名", name = "username", paramType = "query", required = true)
  @GetMapping("/auth/forget/sms/code")
  public InvokeResult<Void> sendSmsCodeByForget(
      @ApiParam(value = "用户名", required = true) @NotBlank(message = "用户名不能为空！") String username) {

    SysConfig sysConfig = sysConfigService.get();
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
      code = numberProducer.createText();
      redisHandler.set(key, code, 15 * 60 * 1000L);
    }

    String captcha = code;

    ThreadUtil.execAsync(new DefaultRunnable(SecurityUtil.getCurrentUser(), () -> {
      aliSmsService.send(user.getTelephone(), sysConfig.getSignName(), sysConfig.getTemplateCode(),
          Collections.singletonMap("code", captcha));
    }));

    return InvokeResultBuilder.success();
  }

  @ApiOperation("根据短信验证码重置密码")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "用户名", name = "username", paramType = "query", required = true),
      @ApiImplicitParam(value = "新密码", name = "password", paramType = "query", required = true),
      @ApiImplicitParam(value = "验证码", name = "captcha", paramType = "query", required = true)})
  @PostMapping("/auth/forget/sms")
  public InvokeResult<Void> updatePswBySms(@NotBlank(message = "用户名不能为空！") String username,
      @NotBlank(message = "新密码不能为空！") @Pattern(regexp = PatternPool.PATTERN_STR_PASSWORD, message = "密码长度必须为5-16位，只允许包含大写字母、小写字母、数字、下划线") String password,
      @NotBlank(message = "验证码不能为空！") String captcha) {

    SysConfig sysConfig = sysConfigService.get();
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

  private LoginDto doLogin(AbstractUserDetails user) {

    if (!user.isAccountNonExpired()) {
      throw new UserLoginException("账户已过期，不允许登录！");
    }

    if (!user.isAccountNonLocked()) {
      throw new UserLoginException("账户已过期，不允许登录！");
    }

    if (!user.isAccountNonLocked()) {
      throw new UserLoginException("账户已锁定，不允许登录！");
    }

    if (!user.isEnabled()) {
      throw new UserLoginException("账户已停用，不允许登录！");
    }

    if (user.isNoPermission()) {
      throw new UserLoginException("账户未授权，不允许登录！");
    }

    // 登录
    StpUtil.login(user.getUsername());

    StpUtil.getSession().set(SecurityConstants.USER_INFO_KEY, user);

    String token = userTokenResolver.getToken();
    ApplicationUtil.publishEvent(new LoginEvent(this, SecurityUtil.getCurrentUser(), token));

    LoginDto dto = new LoginDto(token, user.getName(), user.getPermissions());

    return dto;
  }

  private void checkUserLogin(String username, String password) {
    AbstractUserDetails user = userDetailsService.loadUserByUsername(username);
    if (!passwordEncoderWrapper.getEncoder().matches(password, user.getPassword())) {
      SysConfig config = sysConfigService.get();
      if (config.getAllowLock()) {
        String lockKey = username + "_" + DateUtil.formatDate(LocalDate.now()) + "_LOGIN_LOCK";
        long loginErrorNum = redisHandler.incr(lockKey, 1);
        redisHandler.expire(lockKey, 86400000L);
        if (loginErrorNum < config.getFailNum()) {
          throw new UserLoginException(
              "您已经登录失败" + loginErrorNum + "次，您还可以尝试" + (config.getFailNum() - loginErrorNum)
                  + "次！");
        } else {
          userService.lockById(user.getId());

          userService.cleanCacheByKey(user.getId());

          redisHandler.expire(lockKey, 1L);
          // 锁定用户
          throw new UserLoginException("用户已锁定，无法登录！");
        }
      }
      throw new UserLoginException("用户名或密码错误！");
    }
  }
}
