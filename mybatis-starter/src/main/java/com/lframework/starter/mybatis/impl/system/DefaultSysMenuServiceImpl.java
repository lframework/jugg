package com.lframework.starter.mybatis.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.entity.DefaultSysMenu;
import com.lframework.starter.mybatis.enums.DefaultOpLogType;
import com.lframework.starter.mybatis.enums.system.SysMenuComponentType;
import com.lframework.starter.mybatis.enums.system.SysMenuDisplay;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.mappers.system.DefaultSysMenuMapper;
import com.lframework.starter.mybatis.service.system.SysMenuService;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.vo.system.menu.CreateSysMenuVo;
import com.lframework.starter.mybatis.vo.system.menu.SysMenuSelectorVo;
import com.lframework.starter.mybatis.vo.system.menu.UpdateSysMenuVo;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.starter.web.common.security.SecurityConstants;
import java.io.Serializable;
import java.util.List;
import lombok.NonNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 系统菜单 服务实现类
 * </p>
 *
 * @author zmj
 * @since 2021-05-10
 */
public class DefaultSysMenuServiceImpl extends
    BaseMpServiceImpl<DefaultSysMenuMapper, DefaultSysMenu>
    implements SysMenuService {

  @Override
  public List<DefaultSysMenu> queryList() {

    return this.doQuery();
  }

  @Override
  public List<DefaultSysMenu> getByRoleId(String roleId) {

    return this.doGetByRoleId(roleId);
  }

  @Cacheable(value = DefaultSysMenu.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public DefaultSysMenu findById(@NonNull String id) {

    return this.doGetById(id);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "新增菜单，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(@NonNull CreateSysMenuVo vo) {

    DefaultSysMenu data = this.doCreate(vo);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "修改菜单，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(@NonNull UpdateSysMenuVo vo) {

    DefaultSysMenu oriMenu = this.findById(vo.getId());

    if (!ObjectUtil.equals(vo.getDisplay(), oriMenu.getDisplay().getCode())) {
      throw new DefaultClientException("菜单【" + oriMenu.getTitle() + "】" + "不允许更改类型！");
    }

    DefaultSysMenu data = this.doUpdate(vo);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "删除菜单，ID：{}", params = "#id")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(@NonNull String id) {

    DefaultSysMenu oriMenu = this.findById(id);

    List<DefaultSysMenu> children = this.doGetChildrenById(id);
    if (CollectionUtil.isNotEmpty(children)) {
      //如果子节点不为空
      throw new DefaultClientException("菜单【" + oriMenu.getTitle() + "】存在子菜单，无法删除！");
    }

    this.doDeleteById(id);
  }

  @Override
  public List<DefaultSysMenu> selector(SysMenuSelectorVo vo) {

    return this.doSelector(vo);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "启用菜单，ID：{}", params = "#ids", loopFormat = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchEnable(@NonNull List<String> ids, @NonNull String userId) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    this.doBatchEnable(ids, userId);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "停用菜单，ID：{}", params = "#ids", loopFormat = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchUnable(@NonNull List<String> ids, @NonNull String userId) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    this.doBatchUnable(ids, userId);
  }

  protected List<DefaultSysMenu> doQuery() {

    return getBaseMapper().query();
  }

  protected List<DefaultSysMenu> doGetByRoleId(String roleId) {

    return getBaseMapper().getByRoleId(roleId);
  }

  protected DefaultSysMenu doGetById(@NonNull String id) {

    return getBaseMapper().findById(id);
  }

  protected DefaultSysMenu doCreate(@NonNull CreateSysMenuVo vo) {

    DefaultSysMenu data = new DefaultSysMenu();

    data.setId(IdUtil.getId());
    this.setDataForCreate(vo, data);

    getBaseMapper().insert(data);

    return data;
  }

  protected DefaultSysMenu doUpdate(@NonNull UpdateSysMenuVo vo) {

    DefaultSysMenu data = new DefaultSysMenu();

    data.setId(vo.getId());

    this.setDataForCreate(vo, data);

    data.setAvailable(vo.getAvailable());

    SysMenuService thisService = getThis(this.getClass());

    DefaultSysMenu record = thisService.findById(vo.getId());

    data.setIsSpecial(record.getIsSpecial());

    getBaseMapper().deleteById(vo.getId());

    getBaseMapper().insert(data);

    return data;
  }

  protected void doDeleteById(@NonNull String id) {

    getBaseMapper().deleteById(id);
  }

  protected List<DefaultSysMenu> doSelector(SysMenuSelectorVo vo) {

    return getBaseMapper().selector(vo);
  }

  protected void doBatchEnable(@NonNull List<String> ids, @NonNull String userId) {

    Wrapper<DefaultSysMenu> wrapper = Wrappers.lambdaUpdate(DefaultSysMenu.class)
        .set(DefaultSysMenu::getAvailable, Boolean.TRUE).in(DefaultSysMenu::getId, ids);
    getBaseMapper().update(new DefaultSysMenu(), wrapper);
  }

  protected void doBatchUnable(@NonNull List<String> ids, @NonNull String userId) {

    Wrapper<DefaultSysMenu> wrapper = Wrappers.lambdaUpdate(DefaultSysMenu.class)
        .set(DefaultSysMenu::getAvailable, Boolean.FALSE).in(DefaultSysMenu::getId, ids);
    getBaseMapper().update(new DefaultSysMenu(), wrapper);
  }

  protected void setDataForCreate(@NonNull CreateSysMenuVo vo, @NonNull DefaultSysMenu data) {

    SysMenuDisplay sysMenuDisplay = EnumUtil.getByCode(SysMenuDisplay.class, vo.getDisplay());

    DefaultSysMenu parentMenu = null;
    if (!StringUtil.isBlank(vo.getParentId())) {
      parentMenu = this.findById(vo.getParentId());
      if (parentMenu == null) {
        throw new DefaultClientException("父级菜单不存在！");
      }
      if (parentMenu.getId().equals(data.getId())) {
        throw new DefaultClientException("父级菜单不能是当前菜单！");
      }
    }
    data.setCode(vo.getCode());
    data.setTitle(vo.getTitle());
    data.setParentId(parentMenu == null ? StringPool.EMPTY_STR : parentMenu.getId());
    data.setDisplay(sysMenuDisplay);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    if (sysMenuDisplay == SysMenuDisplay.CATALOG || sysMenuDisplay == SysMenuDisplay.FUNCTION) {
      if (parentMenu != null) {
        //父级菜单必须是目录
        if (parentMenu.getDisplay() != SysMenuDisplay.CATALOG) {
          throw new DefaultClientException("父级菜单类型必须是【" + SysMenuDisplay.CATALOG.getDesc() + "】！");
        }
      }

      data.setName(vo.getName());
      data.setIcon(vo.getIcon());
      data.setPath(vo.getPath());
      data.setHidden(vo.getHidden());

      if (sysMenuDisplay == SysMenuDisplay.FUNCTION) {
        // 功能必须有parentId
        if (parentMenu == null) {
          throw new DefaultClientException(
              "此菜单类型是【" + SysMenuDisplay.FUNCTION.getDesc() + "】，父级菜单不能为空！");
        }
        data.setComponent(vo.getComponent());
        data.setComponentType(
            EnumUtil.getByCode(SysMenuComponentType.class, vo.getComponentType()));
        if (data.getComponentType() == SysMenuComponentType.CUSTOM_FORM) {
          data.setRequestParam(vo.getRequestParam());
        }
        data.setNoCache(vo.getNoCache());

        if (SecurityConstants.PERMISSION_ADMIN_NAME.equals(vo.getPermission())) {
          throw new DefaultClientException(
              "权限【" + SecurityConstants.PERMISSION_ADMIN_NAME + "】为内置权限，请修改！");
        }

        data.setPermission(vo.getPermission());
      }
    } else if (sysMenuDisplay == SysMenuDisplay.PERMISSION) {

      if (parentMenu != null) {
        //父级菜单必须是目录
        if (parentMenu.getDisplay() != SysMenuDisplay.FUNCTION) {
          throw new DefaultClientException("父级菜单类型必须是【" + SysMenuDisplay.FUNCTION.getDesc() + "】！");
        }
      } else {
        throw new DefaultClientException(
            "此菜单类型是【" + SysMenuDisplay.PERMISSION.getDesc() + "】，父级菜单不能为空！");
      }

      if (SecurityConstants.PERMISSION_ADMIN_NAME.equals(vo.getPermission())) {
        throw new DefaultClientException(
            "权限【" + SecurityConstants.PERMISSION_ADMIN_NAME + "】为内置权限，请修改！");
      }

      data.setPermission(vo.getPermission());
    }
  }

  protected List<DefaultSysMenu> doGetChildrenById(String id) {

    return getBaseMapper().getChildrenById(id);
  }

  @CacheEvict(value = DefaultSysMenu.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
