package com.lframework.starter.mybatis.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.dto.system.menu.DefaultSysMenuDto;
import com.lframework.starter.mybatis.entity.DefaultSysMenu;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.enums.system.SysMenuDisplay;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.mappers.system.DefaultSysMenuMapper;
import com.lframework.starter.mybatis.service.system.ISysMenuService;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.vo.system.menu.CreateSysMenuVo;
import com.lframework.starter.mybatis.vo.system.menu.SysMenuSelectorVo;
import com.lframework.starter.mybatis.vo.system.menu.UpdateSysMenuVo;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.web.common.security.SecurityConstants;
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
    implements ISysMenuService {

  @Override
  public List<DefaultSysMenuDto> queryList() {

    return this.doQuery();
  }

  @Override
  public List<DefaultSysMenuDto> getByRoleId(String roleId) {

    return this.doGetByRoleId(roleId);
  }

  @Cacheable(value = DefaultSysMenuDto.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public DefaultSysMenuDto findById(@NonNull String id) {

    return this.doGetById(id);
  }

  @OpLog(type = OpLogType.OTHER, name = "新增菜单，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public String create(@NonNull CreateSysMenuVo vo) {

    DefaultSysMenu data = this.doCreate(vo);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "修改菜单，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public void update(@NonNull UpdateSysMenuVo vo) {

    DefaultSysMenuDto oriMenu = this.findById(vo.getId());

    if (!ObjectUtil.equals(vo.getDisplay(), oriMenu.getDisplay().getCode())) {
      throw new DefaultClientException("菜单【" + oriMenu.getTitle() + "】" + "不允许更改类型！");
    }

    DefaultSysMenu data = this.doUpdate(vo);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = OpLogType.OTHER, name = "删除菜单，ID：{}", params = "#id")
  @Transactional
  @Override
  public void deleteById(@NonNull String id) {

    DefaultSysMenuDto oriMenu = this.findById(id);

    List<DefaultSysMenuDto> children = this.doGetChildrenById(id);
    if (CollectionUtil.isNotEmpty(children)) {
      //如果子节点不为空
      throw new DefaultClientException("菜单【" + oriMenu.getTitle() + "】存在子菜单，无法删除！");
    }

    this.doDeleteById(id);
  }

  @Override
  public List<DefaultSysMenuDto> selector(SysMenuSelectorVo vo) {

    return this.doSelector(vo);
  }

  @OpLog(type = OpLogType.OTHER, name = "启用菜单，ID：{}", params = "#ids", loopFormat = true)
  @Transactional
  @Override
  public void batchEnable(@NonNull List<String> ids, @NonNull String userId) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    this.doBatchEnable(ids, userId);
  }

  @OpLog(type = OpLogType.OTHER, name = "停用菜单，ID：{}", params = "#ids", loopFormat = true)
  @Transactional
  @Override
  public void batchUnable(@NonNull List<String> ids, @NonNull String userId) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    this.doBatchUnable(ids, userId);
  }

  protected List<DefaultSysMenuDto> doQuery() {

    return getBaseMapper().query();
  }

  protected List<DefaultSysMenuDto> doGetByRoleId(String roleId) {

    return getBaseMapper().getByRoleId(roleId);
  }

  protected DefaultSysMenuDto doGetById(@NonNull String id) {

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

    ISysMenuService thisService = getThis(this.getClass());

    DefaultSysMenuDto record = thisService.findById(vo.getId());

    data.setIsSpecial(record.getIsSpecial());

    getBaseMapper().deleteById(vo.getId());

    getBaseMapper().insert(data);

    return data;
  }

  protected void doDeleteById(@NonNull String id) {

    getBaseMapper().deleteById(id);
  }

  protected List<DefaultSysMenuDto> doSelector(SysMenuSelectorVo vo) {

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

    DefaultSysMenuDto parentMenu = null;
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
      /*data.setIcon(vo.getIcon());*/
      data.setPath(vo.getPath());
      data.setHidden(vo.getHidden());

      if (sysMenuDisplay == SysMenuDisplay.FUNCTION) {
        // 功能必须有parentId
        if (parentMenu == null) {
          throw new DefaultClientException(
              "此菜单类型是【" + SysMenuDisplay.FUNCTION.getDesc() + "】，父级菜单不能为空！");
        }
        data.setComponent(vo.getComponent());
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

  protected List<DefaultSysMenuDto> doGetChildrenById(String id) {

    return getBaseMapper().getChildrenById(id);
  }

  @CacheEvict(value = DefaultSysMenuDto.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(String key) {

  }
}
