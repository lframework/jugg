package com.lframework.starter.web.inner.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.codec.Base64;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.code.kaptcha.Producer;
import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.exceptions.impl.UserLoginException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.RegUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.config.properties.KaptchaProperties;
import com.lframework.starter.web.core.annotations.openapi.OpenApi;
import com.lframework.starter.web.core.annotations.oplog.OpLog;
import com.lframework.starter.web.core.components.captcha.CaptchaValidator;
import com.lframework.starter.web.core.components.permission.SysDataPermissionDataPermissionType;
import com.lframework.starter.web.core.components.redis.RedisHandler;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.security.AbstractUserDetails;
import com.lframework.starter.web.core.components.security.UserTokenResolver;
import com.lframework.starter.web.core.components.security.PasswordEncoderWrapper;
import com.lframework.starter.web.core.components.security.SecurityConstants;
import com.lframework.starter.web.core.components.security.SecurityUtil;
import com.lframework.starter.web.core.components.security.UserDetailsService;
import com.lframework.starter.web.core.components.tenant.TenantContextHolder;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.dto.GenerateCaptchaDto;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.core.utils.JsonUtil;
import com.lframework.starter.web.core.utils.TenantUtil;
import com.lframework.starter.web.inner.bo.auth.CollectMenuBo;
import com.lframework.starter.web.inner.bo.auth.LoginBo;
import com.lframework.starter.web.inner.bo.auth.MenuBo;
import com.lframework.starter.web.inner.bo.auth.MenuBo.MetaBo;
import com.lframework.starter.web.inner.components.oplog.AuthOpLogType;
import com.lframework.starter.web.inner.dto.system.LoginDto;
import com.lframework.starter.web.inner.dto.system.MenuDto;
import com.lframework.starter.web.inner.entity.SysDataPermissionData;
import com.lframework.starter.web.inner.entity.SysUserDept;
import com.lframework.starter.web.inner.entity.SysUserRole;
import com.lframework.starter.web.inner.entity.Tenant;
import com.lframework.starter.web.inner.enums.system.SysDataPermissionDataBizType;
import com.lframework.starter.web.inner.enums.system.SysMenuComponentType;
import com.lframework.starter.web.inner.enums.system.SysMenuDisplay;
import com.lframework.starter.web.inner.service.SysConfService;
import com.lframework.starter.web.inner.service.SysModuleTenantService;
import com.lframework.starter.web.inner.service.TenantService;
import com.lframework.starter.web.inner.service.system.SysDataPermissionDataService;
import com.lframework.starter.web.inner.service.system.SysDataPermissionModelDetailService;
import com.lframework.starter.web.inner.service.system.SysMenuService;
import com.lframework.starter.web.inner.service.system.SysUserDeptService;
import com.lframework.starter.web.inner.service.system.SysUserRoleService;
import com.lframework.starter.web.inner.service.system.SysUserService;
import com.lframework.starter.web.inner.vo.system.permission.SysDataPermissionModelDetailVo;
import com.lframework.starter.web.inner.vo.system.user.GetLoginCaptchaRequieVo;
import com.lframework.starter.web.inner.vo.system.user.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

  @Autowired
  private KaptchaProperties kaptchaProperties;

  @Autowired
  private RedisHandler redisHandler;

  @Autowired
  private CaptchaValidator captchaValidator;

  @Autowired
  private PasswordEncoderWrapper passwordEncoderWrapper;

  @Autowired
  private SysMenuService sysMenuService;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private SysUserService sysUserService;

  @Autowired
  private UserTokenResolver userTokenResolver;

  @Autowired
  private TenantService tenantService;

  @Autowired
  private SysDataPermissionDataService sysDataPermissionDataService;

  @Autowired
  private SysDataPermissionModelDetailService sysDataPermissionModelDetailService;

  @Autowired
  private SysUserRoleService sysUserRoleService;

  @Autowired
  private SysUserDeptService sysUserDeptService;

  @Autowired
  private SysModuleTenantService sysModuleTenantService;

  @Autowired
  private SysConfService sysConfService;

  /**
   * 是否为多租户
   */
  @ApiOperation(value = "是否为多租户")
  @OpenApi
  @GetMapping("/auth/tenant/require")
  public InvokeResult<Boolean> getTenantRequire() {
    return InvokeResultBuilder.success(TenantUtil.enableTenant());
  }

  /**
   * 是否需要登录验证码
   */
  @ApiOperation(value = "是否需要登录验证码")
  @OpenApi
  @PostMapping("/auth/captcha/require")
  public InvokeResult<Boolean> getLoginCaptchaRequire(@Valid GetLoginCaptchaRequieVo vo) {
    String username = vo.getUsername();
    String tenantId = null;
    if (TenantUtil.enableTenant()) {
      // 检查租户是否存在
      Wrapper<Tenant> queryTenantWrapper = Wrappers.lambdaQuery(Tenant.class)
          .eq(Tenant::getName, vo.getTenantName());
      Tenant tenant = tenantService.getOne(queryTenantWrapper);
      if (tenant == null) {
        throw new DefaultClientException("用户名或密码错误！");
      }

      if (!tenant.getAvailable()) {
        throw new DefaultClientException("用户已停用，无法登录！");
      }

      tenantId = tenant.getId().toString();

      TenantContextHolder.setTenantId(tenant.getId());
    }

    String loginCaptchaEnabled = sysConfService.findByKey("login-captcha.enabled", "true");
    return InvokeResultBuilder.success(Boolean.valueOf(loginCaptchaEnabled));
  }

  /**
   * 获取登录验证码
   */
  @ApiOperation(value = "获取登录验证码")
  @OpenApi
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
  @OpenApi
  @OpLog(type = AuthOpLogType.class, name = "用户登录")
  @PostMapping("/auth/login")
  public InvokeResult<LoginBo> login(@Valid LoginVo vo) {

    String username = vo.getUsername();
    String password = vo.getPassword();
    String tenantId = null;
    if (TenantUtil.enableTenant()) {
      if (StringUtil.isBlank(vo.getTenantName())) {
        throw new DefaultClientException("用户名或密码错误！");
      }

      username = vo.getUsername();

      // 检查租户是否存在
      Wrapper<Tenant> queryTenantWrapper = Wrappers.lambdaQuery(Tenant.class)
          .eq(Tenant::getName, vo.getTenantName());
      Tenant tenant = tenantService.getOne(queryTenantWrapper);
      if (tenant == null) {
        throw new DefaultClientException("用户名或密码错误！");
      }

      if (!tenant.getAvailable()) {
        throw new DefaultClientException("用户已停用，无法登录！");
      }

      tenantId = tenant.getId().toString();

      TenantContextHolder.setTenantId(tenant.getId());
    }

    log.info("用户 {} {} 开始登录", tenantId, username);

    String loginCaptchaEnabled = sysConfService.findByKey("login-captcha.enabled", "true");
    log.info("当前用户登录需要验证码 = {}", loginCaptchaEnabled);
    if (Boolean.valueOf(loginCaptchaEnabled)) {
      String sn = vo.getSn();
      String captcha = vo.getCaptcha();
      captchaValidator.validate(sn, captcha);
    }

    this.checkUserLogin(tenantId == null ? null : Integer.valueOf(tenantId), username, password);

    AbstractUserDetails user = userDetailsService.loadUserByUsername(username);

    LoginDto dto = this.doLogin(user);

    this.addAttributesToSession(user);

    return InvokeResultBuilder.success(new LoginBo(dto));
  }

  @ApiOperation("退出登录")
  @OpenApi
  @OpLog(type = AuthOpLogType.class, name = "退出登录")
  @PostMapping("/auth/logout")
  public InvokeResult<Void> logout() {

    StpUtil.logout();

    return InvokeResultBuilder.success();
  }

  @ApiOperation(value = "获取用户信息")
  @GetMapping("/auth/info")
  public InvokeResult<LoginBo> info() {

    AbstractUserDetails user = getCurrentUser();
    LoginDto info = new LoginDto(null, user.getName(), user.getPermissions());

    return InvokeResultBuilder.success(new LoginBo(info));
  }

  @ApiOperation("获取用户菜单")
  @GetMapping("/auth/menus")
  public InvokeResult<List<MenuBo>> menus() {

    AbstractUserDetails user = getCurrentUser();
    // 先查询当前租户使用的module
    List<Integer> moduleIds = null;
    if (TenantUtil.enableTenant()) {
      moduleIds = sysModuleTenantService.getAvailableModuleIdsByTenantId(
          TenantContextHolder.getTenantId());
    }
    List<MenuDto> menus = sysMenuService.getMenuByUserId(user.getId(), user.isAdmin(), moduleIds);

    // 组装成树形菜单
    List<MenuDto> topMenus = menus.stream().filter(t -> StringUtil.isBlank(t.getParentId()))
        .collect(Collectors.toList());

    List<MenuBo> results = new ArrayList<>();
    for (MenuDto topMenu : topMenus) {
      MenuBo menuBo = new MenuBo();
      menuBo.setName(topMenu.getName());
      menuBo.setComponent("LAYOUT");
      menuBo.setChildren(parseChildrenMenu(topMenu, menus));
      menuBo.setPath(topMenu.getPath());

      MenuBo.MetaBo meta = new MetaBo();
      meta.setId(topMenu.getId());
      meta.setTitle(topMenu.getMeta().getTitle());
      meta.setIcon(topMenu.getMeta().getIcon());
      meta.setHideMenu(topMenu.getHidden());
      meta.setIgnoreKeepAlive(topMenu.getMeta().getNoCache());
      meta.setIsCollect(topMenu.getIsCollect());

      menuBo.setMeta(meta);

      results.add(menuBo);
    }

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("验证当前登录人的登录密码")
  @PostMapping("/auth/check/password")
  public InvokeResult<Boolean> checkPassword(
      @NotBlank(message = "登录密码不能为空！") String password) {
    return InvokeResultBuilder.success(passwordEncoderWrapper.getEncoder()
        .matches(password, SecurityUtil.getCurrentUser().getPassword()));
  }

  private List<MenuBo> parseChildrenMenu(MenuDto topMenu, List<MenuDto> menus) {
    List<MenuBo> children = menus.stream()
        .filter(t -> StringUtil.equals(t.getParentId(), topMenu.getId())).map(t -> {
          MenuBo menuBo = new MenuBo();
          menuBo.setName(t.getName());
          menuBo.setChildren(parseChildrenMenu(t, menus));

          menuBo.setComponent(t.getComponent());
          menuBo.setPath(
              StringUtil.startWith(t.getPath(), "/") ? t.getPath().substring(1) : t.getPath());
          MenuBo.MetaBo meta = new MetaBo();
          meta.setId(t.getId());
          meta.setTitle(t.getMeta().getTitle());
          meta.setIcon(t.getMeta().getIcon());
          meta.setHideMenu(t.getHidden());
          meta.setIgnoreKeepAlive(t.getMeta().getNoCache());
          meta.setIsCollect(t.getIsCollect());
          if (RegUtil.isMatch(PatternPool.PATTERN_HTTP_URL, menuBo.getPath())) {
            meta.setIsLink(Boolean.TRUE);
          }

          // 如果是功能
          if (SysMenuDisplay.FUNCTION.getCode().equals(t.getDisplay())) {
            // 普通
            if (SysMenuComponentType.NORMAL.getCode().equals(t.getComponentType())) {
              if ("/iframes/index".equalsIgnoreCase(t.getComponent())) {
                menuBo.setComponent(null);
                meta.setFrameSrc(menuBo.getPath().substring(menuBo.getPath().indexOf("?src=") + 5));
                menuBo.setPath(menuBo.getPath().substring(0, menuBo.getPath().indexOf("?src=")));
              }
            } else if (SysMenuComponentType.CUSTOM_LIST.getCode().equals(t.getComponentType())) {
              // 自定义列表
              menuBo.setComponent("CUSTOMLIST");
              meta.setCustomListId(t.getComponent());
            } else if (SysMenuComponentType.CUSTOM_PAGE.getCode().equals(t.getComponentType())) {
              // 自定义页面
              menuBo.setComponent("CUSTOMPAGE");
              meta.setCustomPageId(t.getComponent());
            }
          }

          menuBo.setMeta(meta);

          return menuBo;
        }).collect(Collectors.toList());

    return children;
  }

  @ApiOperation("收藏菜单")
  @ApiImplicitParam(value = "菜单ID", name = "menuId", paramType = "query")
  @PostMapping("/menu/collect")
  public InvokeResult<Void> collectMenu(String menuId) {

    AbstractUserDetails user = getCurrentUser();
    sysMenuService.collect(user.getId(), menuId);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("获取已收藏的菜单")
  @GetMapping("/menu/collect")
  public InvokeResult<List<CollectMenuBo>> getCollectMenus() {
    AbstractUserDetails user = getCurrentUser();
    // 先查询当前租户使用的module
    List<Integer> moduleIds = null;
    if (TenantUtil.enableTenant()) {
      moduleIds = sysModuleTenantService.getAvailableModuleIdsByTenantId(
          TenantContextHolder.getTenantId());
    }
    List<MenuDto> menus = sysMenuService.getMenuByUserId(user.getId(), user.isAdmin(), moduleIds);

    List<MenuDto> collectMenus = menus.stream().filter(t -> t.getIsCollect())
        .collect(Collectors.toList());
    List<CollectMenuBo> results = collectMenus.stream().map(t -> {
      CollectMenuBo result = new CollectMenuBo();
      result.setId(t.getId());
      result.setTitle(t.getMeta().getTitle());
      result.setIcon(t.getMeta().getIcon());
      if (StringUtil.isBlank(result.getIcon())) {
        // 如果没有图标 那么就往上级找
        String icon = null;
        String parentId = t.getParentId();
        while (StringUtil.isNotEmpty(parentId)) {
          MenuDto parentMenu = null;
          for (MenuDto m : menus) {
            if (m.getId().equals(parentId)) {
              parentMenu = m;
            }
          }

          if (parentMenu == null) {
            break;
          }

          if (StringUtil.isNotBlank(parentMenu.getMeta().getIcon())) {
            icon = parentMenu.getMeta().getIcon();
            break;
          }

          parentId = parentMenu.getParentId();
        }
        result.setIcon(icon);
      }

      List<String> pathList = new ArrayList<>();
      pathList.add(t.getPath());
      String parentId = t.getParentId();
      while (StringUtil.isNotEmpty(parentId)) {
        MenuDto parentMenu = null;
        for (MenuDto m : menus) {
          if (m.getId().equals(parentId)) {
            parentMenu = m;
          }
        }

        if (parentMenu == null) {
          break;
        }

        if (StringUtil.isNotBlank(parentMenu.getPath())) {
          pathList.add(parentMenu.getPath());
        }

        parentId = parentMenu.getParentId();
      }

      pathList = CollectionUtil.reverse(pathList);
      result.setPath(CollectionUtil.join(pathList, ""));

      return result;
    }).collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("取消收藏菜单")
  @ApiImplicitParam(value = "菜单ID", name = "menuId", paramType = "query")
  @PostMapping("/menu/collect/cancel")
  public InvokeResult<Void> cancelCollectMenu(String menuId) {

    AbstractUserDetails user = getCurrentUser();
    sysMenuService.cancelCollect(user.getId(), menuId);

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
    // loginId需要唯一
    String loginId =
        (user.getTenantId() == null ? "" : user.getTenantId() + "@") + user.getUsername() + "@"
            + IdUtil.getUUID();

    StpUtil.login(loginId);

    user.setLoginId(loginId);

    StpUtil.getSession().set(SecurityConstants.USER_INFO_KEY, user);

    String token = userTokenResolver.getToken();

    return new LoginDto(token, user.getName(), user.getPermissions());
  }

  private void checkUserLogin(Integer tenantId, String username, String password) {
    AbstractUserDetails user = userDetailsService.loadUserByUsername(username);
    String lockKey =
        (tenantId == null ? "noTenant" : tenantId) + "_" + username + "_" + DateUtil.formatDate(
            LocalDate.now()) + "_LOGIN_LOCK";
    if (!passwordEncoderWrapper.getEncoder().matches(password, user.getPassword())) {
      long loginErrorNum = redisHandler.incr(lockKey, 1);
      redisHandler.expire(lockKey, 86400000L);
      int failNum = 5;
      if (loginErrorNum < failNum) {
        throw new UserLoginException(
            "您已经登录失败" + loginErrorNum + "次，您还可以尝试" + (failNum - loginErrorNum)
                + "次！");
      } else {
        sysUserService.lockById(user.getId());

        sysUserService.cleanCacheByKey(user.getId());

        redisHandler.expire(lockKey, 1L);
        // 锁定用户
        throw new UserLoginException("用户已锁定，无法登录！");
      }
    } else {
      redisHandler.expire(lockKey, 1L);
    }
  }

  protected void addAttributesToSession(AbstractUserDetails user) {
    Map<String, String> dataPermissionMap = new HashMap<>();
    Map<String, SysDataPermissionDataPermissionType> beans = ApplicationUtil.getBeansOfType(SysDataPermissionDataPermissionType.class);
    Collection<SysDataPermissionDataPermissionType> permissionTypes = beans.values();
    for (SysDataPermissionDataPermissionType permissionType : permissionTypes) {
      List<String> sqlTemplates = new ArrayList<>();

      List<SysUserRole> userRoles = sysUserRoleService.getByUserId(user.getId());
      if (CollectionUtil.isNotEmpty(userRoles)) {
        for (SysUserRole userRole : userRoles) {
          SysDataPermissionData permissionData = sysDataPermissionDataService.getByBizId(
              userRole.getRoleId(),
              SysDataPermissionDataBizType.ROLE.getCode(), permissionType.getCode());
          if (permissionData != null) {
            String sqlTemplate = sysDataPermissionModelDetailService.toSql(
                JsonUtil.parseList(permissionData.getPermission(),
                    SysDataPermissionModelDetailVo.class));
            if (StringUtil.isNotBlank(sqlTemplate)) {
              sqlTemplates.add(sqlTemplate);
            }
          }
        }
      }

      List<SysUserDept> userDepts = sysUserDeptService.getByUserId(user.getId());
      if (CollectionUtil.isNotEmpty(userDepts)) {
        for (SysUserDept userDept : userDepts) {
          SysDataPermissionData permissionData = sysDataPermissionDataService.getByBizId(
              userDept.getDeptId(),
              SysDataPermissionDataBizType.DEPT.getCode(), permissionType.getCode());
          if (permissionData != null) {
            String sqlTemplate = sysDataPermissionModelDetailService.toSql(
                JsonUtil.parseList(permissionData.getPermission(),
                    SysDataPermissionModelDetailVo.class));
            if (StringUtil.isNotBlank(sqlTemplate)) {
              sqlTemplates.add(sqlTemplate);
            }
          }
        }
      }

      SysDataPermissionData permissionData = sysDataPermissionDataService.getByBizId(
          user.getId(),
          SysDataPermissionDataBizType.USER.getCode(), permissionType.getCode());
      if (permissionData != null) {
        String sqlTemplate = sysDataPermissionModelDetailService.toSql(
            JsonUtil.parseList(permissionData.getPermission(),
                SysDataPermissionModelDetailVo.class));
        if (StringUtil.isNotBlank(sqlTemplate)) {
          sqlTemplates.add(sqlTemplate);
        }
      }

      if (CollectionUtil.isNotEmpty(sqlTemplates)) {
        dataPermissionMap.put(permissionType.getCode().toString(),
            "(" + CollectionUtil.join(sqlTemplates, " AND ") + ")");
      }
    }

    StpUtil.getSession().set(SecurityConstants.DATA_PERMISSION_SQL_MAP, dataPermissionMap);

    Map<String, String> dataPermissionVar = new HashMap<>();
    List<SysUserDept> userDepts = sysUserDeptService.getByUserId(user.getId());
    List<String> curDeptIds = userDepts.stream().map(SysUserDept::getDeptId)
        .map(t -> "'" + t + "'").collect(
            Collectors.toList());
    dataPermissionVar.put("curDeptIds",
        CollectionUtil.isEmpty(curDeptIds) ? IdUtil.getId() : CollectionUtil.join(curDeptIds, ","));

    StpUtil.getSession().set(SecurityConstants.DATA_PERMISSION_SQL_VAR, dataPermissionVar);
  }
}
