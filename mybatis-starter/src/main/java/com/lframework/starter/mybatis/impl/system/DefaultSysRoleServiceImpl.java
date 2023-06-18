package com.lframework.starter.mybatis.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.entity.DefaultSysRole;
import com.lframework.starter.mybatis.enums.DefaultOpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.mappers.system.DefaultSysRoleMapper;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.system.SysMenuService;
import com.lframework.starter.mybatis.service.system.SysRoleService;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.mybatis.vo.system.role.CreateSysRoleVo;
import com.lframework.starter.mybatis.vo.system.role.QuerySysRoleVo;
import com.lframework.starter.mybatis.vo.system.role.SysRoleSelectorVo;
import com.lframework.starter.mybatis.vo.system.role.UpdateSysRoleVo;
import com.lframework.starter.web.common.security.SecurityConstants;
import com.lframework.starter.web.utils.IdUtil;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

public class DefaultSysRoleServiceImpl extends
    BaseMpServiceImpl<DefaultSysRoleMapper, DefaultSysRole>
    implements SysRoleService {

  @Autowired
  private SysMenuService sysMenuService;

  @Override
  public PageResult<DefaultSysRole> query(Integer pageIndex, Integer pageSize,
      QuerySysRoleVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<DefaultSysRole> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<DefaultSysRole> query(QuerySysRoleVo vo) {

    return this.doQuery(vo);
  }

  @Cacheable(value = DefaultSysRole.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public DefaultSysRole findById(String id) {

    return this.doGetById(id);
  }

  @Override
  public PageResult<DefaultSysRole> selector(Integer pageIndex, Integer pageSize,
      SysRoleSelectorVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<DefaultSysRole> datas = this.doSelector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "停用角色，ID：{}", params = "#ids", loopFormat = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchUnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    for (String id : ids) {
      DefaultSysRole role = this.findById(id);
      if (SecurityConstants.PERMISSION_ADMIN_NAME.equals(role.getPermission())) {
        throw new DefaultClientException(
            "角色【" + role.getName() + "】的权限为【" + SecurityConstants.PERMISSION_ADMIN_NAME
                + "】，不允许停用！");
      }
    }

    this.doBatchUnable(ids);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "启用角色，ID：{}", params = "#ids", loopFormat = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchEnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    for (String id : ids) {
      DefaultSysRole role = this.findById(id);
      if (SecurityConstants.PERMISSION_ADMIN_NAME.equals(role.getPermission())) {
        throw new DefaultClientException(
            "角色【" + role.getName() + "】的权限为【" + SecurityConstants.PERMISSION_ADMIN_NAME
                + "】，不允许启用！");
      }
    }

    this.doBatchEnable(ids);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "新增角色，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateSysRoleVo vo) {

    if (!StringUtil.isBlank(vo.getPermission())) {

      if (SecurityConstants.PERMISSION_ADMIN_NAME.equals(vo.getPermission())) {
        throw new DefaultClientException(
            "权限【" + SecurityConstants.PERMISSION_ADMIN_NAME + "】为内置权限，请修改！");
      }

      // 这里的权限不能与菜单权限重复
      if (sysMenuService.existPermission(vo.getPermission())) {
        throw new DefaultClientException(
            "权限【" + vo.getPermission() + "】为菜单权限，请修改！");
      }
    }

    DefaultSysRole data = this.doCreate(vo);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "修改角色，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateSysRoleVo vo) {

    DefaultSysRole data = this.findById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("角色不存在！");
    }

    if (SecurityConstants.PERMISSION_ADMIN_NAME.equals(data.getPermission())) {
      throw new DefaultClientException("角色【" + data.getName() + "】为内置角色，不允许修改！");
    }

    if (!StringUtil.isBlank(vo.getPermission())) {

      if (SecurityConstants.PERMISSION_ADMIN_NAME.equals(vo.getPermission())) {
        throw new DefaultClientException(
            "权限【" + SecurityConstants.PERMISSION_ADMIN_NAME + "】为内置权限，请修改！");
      }

      // 这里的权限不能与菜单权限重复
      if (sysMenuService.existPermission(vo.getPermission())) {
        throw new DefaultClientException(
            "权限【" + vo.getPermission() + "】为菜单权限，请修改！");
      }
    }

    this.doUpdate(vo);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Override
  public List<DefaultSysRole> getByUserId(String userId) {

    return this.doGetByUserId(userId);
  }

  protected List<DefaultSysRole> doQuery(QuerySysRoleVo vo) {

    return getBaseMapper().query(vo);
  }

  protected DefaultSysRole doGetById(String id) {

    return getBaseMapper().findById(id);
  }

  protected List<DefaultSysRole> doSelector(SysRoleSelectorVo vo) {

    return getBaseMapper().selector(vo);
  }

  protected void doBatchUnable(Collection<String> ids) {

    Wrapper<DefaultSysRole> updateWrapper = Wrappers.lambdaUpdate(DefaultSysRole.class)
        .set(DefaultSysRole::getAvailable, Boolean.FALSE).in(DefaultSysRole::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  protected void doBatchEnable(Collection<String> ids) {

    Wrapper<DefaultSysRole> updateWrapper = Wrappers.lambdaUpdate(DefaultSysRole.class)
        .set(DefaultSysRole::getAvailable, Boolean.TRUE).in(DefaultSysRole::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  protected DefaultSysRole doCreate(CreateSysRoleVo vo) {

    Wrapper<DefaultSysRole> checkWrapper = Wrappers.lambdaQuery(DefaultSysRole.class)
        .eq(DefaultSysRole::getCode, vo.getCode());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(DefaultSysRole.class)
        .eq(DefaultSysRole::getName, vo.getName());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    DefaultSysRole data = new DefaultSysRole();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());

    if (!StringUtil.isBlank(vo.getPermission())) {

      data.setPermission(vo.getPermission());
    }

    data.setAvailable(Boolean.TRUE);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    getBaseMapper().insert(data);

    return data;
  }

  protected void doUpdate(UpdateSysRoleVo vo) {

    Wrapper<DefaultSysRole> checkWrapper = Wrappers.lambdaQuery(DefaultSysRole.class)
        .eq(DefaultSysRole::getCode, vo.getCode()).ne(DefaultSysRole::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(DefaultSysRole.class)
        .eq(DefaultSysRole::getName, vo.getName())
        .ne(DefaultSysRole::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    LambdaUpdateWrapper<DefaultSysRole> updateWrapper = Wrappers.lambdaUpdate(DefaultSysRole.class)
        .set(DefaultSysRole::getCode, vo.getCode()).set(DefaultSysRole::getName, vo.getName())
        .set(DefaultSysRole::getPermission, null)
        .set(DefaultSysRole::getAvailable, vo.getAvailable())
        .set(DefaultSysRole::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(DefaultSysRole::getId, vo.getId());

    if (!StringUtil.isBlank(vo.getPermission())) {

      updateWrapper.set(DefaultSysRole::getPermission, vo.getPermission());
    }

    getBaseMapper().update(updateWrapper);
  }

  protected List<DefaultSysRole> doGetByUserId(String userId) {

    return getBaseMapper().getByUserId(userId);
  }

  @CacheEvict(value = DefaultSysRole.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
