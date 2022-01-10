package com.lframework.starter.security.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.*;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.dto.system.role.DefaultSysRoleDto;
import com.lframework.starter.security.entity.DefaultSysRole;
import com.lframework.starter.security.mappers.system.DefaultSysRoleMapper;
import com.lframework.starter.security.service.system.ISysRoleService;
import com.lframework.starter.security.vo.system.role.CreateSysRoleVo;
import com.lframework.starter.security.vo.system.role.QuerySysRoleVo;
import com.lframework.starter.security.vo.system.role.SysRoleSelectorVo;
import com.lframework.starter.security.vo.system.role.UpdateSysRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public class DefaultSysRoleServiceImpl implements ISysRoleService {

    @Autowired
    private DefaultSysRoleMapper defaultSysRoleMapper;

    @Override
    public PageResult<DefaultSysRoleDto> query(Integer pageIndex, Integer pageSize, QuerySysRoleVo vo) {

        Assert.greaterThanZero(pageIndex);
        Assert.greaterThanZero(pageSize);

        PageHelperUtil.startPage(pageIndex, pageSize);
        List<DefaultSysRoleDto> datas = this.query(vo);

        return PageResultUtil.convert(new PageInfo<>(datas));
    }

    @Override
    public List<DefaultSysRoleDto> query(QuerySysRoleVo vo) {

        return this.doQuery(vo);
    }

    @Cacheable(value = DefaultSysRoleDto.CACHE_NAME, key = "#id", unless = "#result == null")
    @Override
    public DefaultSysRoleDto getById(String id) {

        return this.doGetById(id);
    }

    @Override
    public PageResult<DefaultSysRoleDto> selector(Integer pageIndex, Integer pageSize, SysRoleSelectorVo vo) {

        Assert.greaterThanZero(pageIndex);
        Assert.greaterThanZero(pageSize);

        PageHelperUtil.startPage(pageIndex, pageSize);
        List<DefaultSysRoleDto> datas = this.doSelector(vo);

        return PageResultUtil.convert(new PageInfo<>(datas));
    }

    @OpLog(type = OpLogType.OTHER, name = "停用角色，ID：{}", params = "#ids", loopFormat = true)
    @Transactional
    @Override
    public void batchUnable(Collection<String> ids) {

        if (CollectionUtil.isEmpty(ids)) {
            return;
        }

        for (String id : ids) {
            DefaultSysRoleDto role = this.getById(id);
            if (StringPool.PERMISSION_ADMIN_NAME.equals(role.getPermission())) {
                throw new DefaultClientException(
                        "角色【" + role.getName() + "】的权限为【" + StringPool.PERMISSION_ADMIN_NAME + "】，不允许停用！");
            }
        }

        this.doBatchUnable(ids);

        ISysRoleService thisService = getThis(this.getClass());
        for (String id : ids) {
            thisService.cleanCacheByKey(id);
        }
    }

    @OpLog(type = OpLogType.OTHER, name = "启用角色，ID：{}", params = "#ids", loopFormat = true)
    @Transactional
    @Override
    public void batchEnable(Collection<String> ids) {

        if (CollectionUtil.isEmpty(ids)) {
            return;
        }

        for (String id : ids) {
            DefaultSysRoleDto role = this.getById(id);
            if (StringPool.PERMISSION_ADMIN_NAME.equals(role.getPermission())) {
                throw new DefaultClientException(
                        "角色【" + role.getName() + "】的权限为【" + StringPool.PERMISSION_ADMIN_NAME + "】，不允许启用！");
            }
        }

        this.doBatchEnable(ids);

        ISysRoleService thisService = getThis(this.getClass());
        for (String id : ids) {
            thisService.cleanCacheByKey(id);
        }
    }

    @OpLog(type = OpLogType.OTHER, name = "新增角色，ID：{}, 编号：{}", params = {"#id", "#code"})
    @Transactional
    @Override
    public String create(CreateSysRoleVo vo) {

        DefaultSysRole data = this.doCreate(vo);

        OpLogUtil.setVariable("id", data.getId());
        OpLogUtil.setVariable("code", vo.getCode());
        OpLogUtil.setExtra(vo);

        return data.getId();
    }

    @OpLog(type = OpLogType.OTHER, name = "修改角色，ID：{}, 编号：{}", params = {"#id", "#code"})
    @Transactional
    @Override
    public void update(UpdateSysRoleVo vo) {

        DefaultSysRoleDto data = this.getById(vo.getId());
        if (ObjectUtil.isNull(data)) {
            throw new DefaultClientException("角色不存在！");
        }

        if (StringPool.PERMISSION_ADMIN_NAME.equals(data.getPermission())) {
            throw new DefaultClientException("角色【" + data.getName() + "】为内置角色，不允许修改！");
        }

        if (!StringUtil.isBlank(vo.getPermission())) {

            if (StringPool.PERMISSION_ADMIN_NAME.equals(vo.getPermission())) {
                throw new DefaultClientException("权限【" + StringPool.PERMISSION_ADMIN_NAME + "】为内置权限，请修改！");
            }
        }

        this.doUpdate(vo);

        OpLogUtil.setVariable("id", data.getId());
        OpLogUtil.setVariable("code", vo.getCode());
        OpLogUtil.setExtra(vo);

        ISysRoleService thisService = getThis(this.getClass());
        thisService.cleanCacheByKey(data.getId());
    }

    @Override
    public List<DefaultSysRoleDto> getByUserId(String userId) {

        return this.doGetByUserId(userId);
    }

    protected List<DefaultSysRoleDto> doQuery(QuerySysRoleVo vo) {

        return defaultSysRoleMapper.query(vo);
    }

    protected DefaultSysRoleDto doGetById(String id) {

        return defaultSysRoleMapper.getById(id);
    }

    protected List<DefaultSysRoleDto> doSelector(SysRoleSelectorVo vo) {

        return defaultSysRoleMapper.selector(vo);
    }

    protected void doBatchUnable(Collection<String> ids) {

        Wrapper<DefaultSysRole> updateWrapper = Wrappers.lambdaUpdate(DefaultSysRole.class)
                .set(DefaultSysRole::getAvailable, Boolean.FALSE).in(DefaultSysRole::getId, ids);
        defaultSysRoleMapper.update(updateWrapper);
    }

    protected void doBatchEnable(Collection<String> ids) {

        Wrapper<DefaultSysRole> updateWrapper = Wrappers.lambdaUpdate(DefaultSysRole.class)
                .set(DefaultSysRole::getAvailable, Boolean.TRUE).in(DefaultSysRole::getId, ids);
        defaultSysRoleMapper.update(updateWrapper);
    }

    protected DefaultSysRole doCreate(CreateSysRoleVo vo) {

        Wrapper<DefaultSysRole> checkWrapper = Wrappers.lambdaQuery(DefaultSysRole.class)
                .eq(DefaultSysRole::getCode, vo.getCode());
        if (defaultSysRoleMapper.selectCount(checkWrapper) > 0) {
            throw new DefaultClientException("编号重复，请重新输入！");
        }

        checkWrapper = Wrappers.lambdaQuery(DefaultSysRole.class)
                .eq(DefaultSysRole::getName, vo.getName());
        if (defaultSysRoleMapper.selectCount(checkWrapper) > 0) {
            throw new DefaultClientException("名称重复，请重新输入！");
        }

        if (!StringUtil.isBlank(vo.getPermission())) {

            if (StringPool.PERMISSION_ADMIN_NAME.equals(vo.getPermission())) {
                throw new DefaultClientException("权限【" + StringPool.PERMISSION_ADMIN_NAME + "】为内置权限，请修改！");
            }
        }

        DefaultSysRole data = new DefaultSysRole();
        data.setId(IdUtil.getId());
        data.setCode(vo.getCode());
        data.setName(vo.getName());

        if (!StringUtil.isBlank(vo.getPermission())) {

            data.setPermission(vo.getPermission());
        }

        data.setAvailable(Boolean.TRUE);
        data.setDescription(StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

        defaultSysRoleMapper.insert(data);

        return data;
    }

    protected void doUpdate(UpdateSysRoleVo vo) {

        Wrapper<DefaultSysRole> checkWrapper = Wrappers.lambdaQuery(DefaultSysRole.class)
                .eq(DefaultSysRole::getCode, vo.getCode()).ne(DefaultSysRole::getId, vo.getId());
        if (defaultSysRoleMapper.selectCount(checkWrapper) > 0) {
            throw new DefaultClientException("编号重复，请重新输入！");
        }

        checkWrapper = Wrappers.lambdaQuery(DefaultSysRole.class)
                .eq(DefaultSysRole::getName, vo.getName()).ne(DefaultSysRole::getId, vo.getId());
        if (defaultSysRoleMapper.selectCount(checkWrapper) > 0) {
            throw new DefaultClientException("名称重复，请重新输入！");
        }

        LambdaUpdateWrapper<DefaultSysRole> updateWrapper = Wrappers.lambdaUpdate(DefaultSysRole.class)
                .set(DefaultSysRole::getCode, vo.getCode()).set(DefaultSysRole::getName, vo.getName())
                .set(DefaultSysRole::getPermission, null).set(DefaultSysRole::getAvailable, vo.getAvailable())
                .set(DefaultSysRole::getDescription,
                        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
                .eq(DefaultSysRole::getId, vo.getId());

        if (!StringUtil.isBlank(vo.getPermission())) {

            updateWrapper.set(DefaultSysRole::getPermission, vo.getPermission());
        }

        defaultSysRoleMapper.update(updateWrapper);
    }

    protected List<DefaultSysRoleDto> doGetByUserId(String userId) {

        return defaultSysRoleMapper.getByUserId(userId);
    }

    @CacheEvict(value = DefaultSysRoleDto.CACHE_NAME, key = "#key")
    @Override
    public void cleanCacheByKey(String key) {

    }
}
