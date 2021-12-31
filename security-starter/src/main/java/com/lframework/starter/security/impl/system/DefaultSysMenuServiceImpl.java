package com.lframework.starter.security.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.security.dto.system.menu.DefaultSysMenuDto;
import com.lframework.starter.security.entity.DefaultSysMenu;
import com.lframework.starter.security.enums.system.SysMenuDisplay;
import com.lframework.starter.security.mappers.system.DefaultSysMenuMapper;
import com.lframework.starter.security.service.system.ISysMenuService;
import com.lframework.starter.security.vo.system.menu.CreateSysMenuVo;
import com.lframework.starter.security.vo.system.menu.SysMenuSelectorVo;
import com.lframework.starter.security.vo.system.menu.UpdateSysMenuVo;
import com.lframework.starter.web.utils.EnumUtil;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 系统菜单 服务实现类
 * </p>
 *
 * @author zmj
 * @since 2021-05-10
 */
public class DefaultSysMenuServiceImpl implements ISysMenuService {

    @Autowired
    private DefaultSysMenuMapper defaultSysMenuMapper;

    @Override
    public List<DefaultSysMenuDto> query() {

        return this.doQuery();
    }

    @Override
    public List<DefaultSysMenuDto> getByRoleId(String roleId) {

        return this.doGetByRoleId(roleId);
    }

    @Cacheable(value = DefaultSysMenuDto.CACHE_NAME, key = "#id", unless = "#result == null")
    @Override
    public DefaultSysMenuDto getById(@NonNull String id) {

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

        DefaultSysMenuDto oriMenu = this.getById(vo.getId());
        if (oriMenu.getIsSpecial()) {
            throw new DefaultClientException("菜单【" + oriMenu.getName() + "】为内置菜单，不允许修改！");
        }

        if (!ObjectUtil.equals(vo.getDisplay(), oriMenu.getDisplay().getCode())) {
            throw new DefaultClientException("菜单【" + oriMenu.getName() + "】" + "不允许更改类型！");
        }

        DefaultSysMenu data = this.doUpdate(vo);

        OpLogUtil.setVariable("id", data.getId());
        OpLogUtil.setVariable("code", vo.getCode());
        OpLogUtil.setExtra(vo);

        ISysMenuService thisService = getThis(this.getClass());
        thisService.cleanCacheByKey(data.getId());
    }

    @OpLog(type = OpLogType.OTHER, name = "删除菜单，ID：{}", params = "#id")
    @Transactional
    @Override
    public void deleteById(@NonNull String id) {

        DefaultSysMenuDto oriMenu = this.getById(id);
        if (oriMenu.getIsSpecial()) {
            throw new DefaultClientException("菜单【" + oriMenu.getName() + "】为内置菜单，不允许删除！");
        }

        List<DefaultSysMenuDto> children = this.doGetChildrenById(id);
        if (CollectionUtil.isNotEmpty(children)) {
            //如果子节点不为空
            throw new DefaultClientException("菜单【" + oriMenu.getName() + "】存在子菜单，无法删除！");
        }

        this.doDeleteById(id);

        ISysMenuService thisService = getThis(this.getClass());
        thisService.cleanCacheByKey(id);
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

        for (String id : ids) {
            DefaultSysMenuDto oriMenu = this.getById(id);
            if (oriMenu.getIsSpecial()) {
                throw new DefaultClientException("菜单【" + oriMenu.getName() + "】为内置菜单，不允许启用！");
            }
        }

        this.doBatchEnable(ids, userId);

        ISysMenuService thisService = getThis(this.getClass());
        for (String id : ids) {
            thisService.cleanCacheByKey(id);
        }
    }

    @OpLog(type = OpLogType.OTHER, name = "停用菜单，ID：{}", params = "#ids", loopFormat = true)
    @Transactional
    @Override
    public void batchUnable(@NonNull List<String> ids, @NonNull String userId) {

        if (CollectionUtil.isEmpty(ids)) {
            return;
        }

        for (String id : ids) {
            DefaultSysMenuDto oriMenu = this.getById(id);
            if (oriMenu.getIsSpecial()) {
                throw new DefaultClientException("菜单【" + oriMenu.getName() + "】为内置菜单，不允许停用！");
            }
        }

        this.doBatchUnable(ids, userId);

        ISysMenuService thisService = getThis(this.getClass());
        for (String id : ids) {
            thisService.cleanCacheByKey(id);
        }
    }

    protected List<DefaultSysMenuDto> doQuery() {

        return defaultSysMenuMapper.query();
    }

    protected List<DefaultSysMenuDto> doGetByRoleId(String roleId) {

        return defaultSysMenuMapper.getByRoleId(roleId);
    }

    protected DefaultSysMenuDto doGetById(@NonNull String id) {

        return defaultSysMenuMapper.getById(id);
    }

    protected DefaultSysMenu doCreate(@NonNull CreateSysMenuVo vo) {

        DefaultSysMenu data = new DefaultSysMenu();

        data.setId(IdUtil.getId());
        this.setDataForCreate(vo, data);

        defaultSysMenuMapper.insert(data);

        return data;
    }

    protected DefaultSysMenu doUpdate(@NonNull UpdateSysMenuVo vo) {

        DefaultSysMenu data = new DefaultSysMenu();

        data.setId(vo.getId());

        this.setDataForCreate(vo, data);

        defaultSysMenuMapper.deleteById(vo.getId());

        defaultSysMenuMapper.insert(data);

        return data;
    }

    protected void doDeleteById(@NonNull String id) {

        defaultSysMenuMapper.deleteById(id);
    }

    protected List<DefaultSysMenuDto> doSelector(SysMenuSelectorVo vo) {

        return defaultSysMenuMapper.selector(vo);
    }

    protected void doBatchEnable(@NonNull List<String> ids, @NonNull String userId) {

        Wrapper<DefaultSysMenu> wrapper = Wrappers.lambdaUpdate(DefaultSysMenu.class)
                .set(DefaultSysMenu::getAvailable, Boolean.TRUE).in(DefaultSysMenu::getId, ids);
        defaultSysMenuMapper.update(new DefaultSysMenu(), wrapper);
    }

    protected void doBatchUnable(@NonNull List<String> ids, @NonNull String userId) {

        Wrapper<DefaultSysMenu> wrapper = Wrappers.lambdaUpdate(DefaultSysMenu.class)
                .set(DefaultSysMenu::getAvailable, Boolean.FALSE).in(DefaultSysMenu::getId, ids);
        defaultSysMenuMapper.update(new DefaultSysMenu(), wrapper);
    }

    protected void setDataForCreate(@NonNull CreateSysMenuVo vo, @NonNull DefaultSysMenu data) {

        SysMenuDisplay sysMenuDisplay = EnumUtil.getByCode(SysMenuDisplay.class, vo.getDisplay());

        data.setCode(vo.getCode());
        data.setTitle(vo.getTitle());
        data.setParentId(vo.getParentId());
        data.setDisplay(sysMenuDisplay);
        data.setDescription(StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

        if (sysMenuDisplay == SysMenuDisplay.CATALOG || sysMenuDisplay == SysMenuDisplay.FUNCTION) {
            data.setName(vo.getName());
            /*data.setIcon(vo.getIcon());*/
            data.setPath(vo.getPath());
            data.setHidden(vo.getHidden());

            if (sysMenuDisplay == SysMenuDisplay.FUNCTION) {
                data.setComponent(vo.getComponent());
                data.setNoCache(vo.getNoCache());

                if (StringPool.PERMISSION_ADMIN_NAME.equals(vo.getPermission())) {
                    throw new DefaultClientException("权限【" + StringPool.PERMISSION_ADMIN_NAME + "】为内置权限，请修改！");
                }

                data.setPermission(vo.getPermission());
            }
        } else if (sysMenuDisplay == SysMenuDisplay.PERMISSION) {

            if (StringPool.PERMISSION_ADMIN_NAME.equals(vo.getPermission())) {
                throw new DefaultClientException("权限【" + StringPool.PERMISSION_ADMIN_NAME + "】为内置权限，请修改！");
            }

            data.setPermission(vo.getPermission());
        }
    }

    protected List<DefaultSysMenuDto> doGetChildrenById(String id) {

        return defaultSysMenuMapper.getChildrenById(id);
    }

    @CacheEvict(value = DefaultSysMenuDto.CACHE_NAME, key = "#key")
    @Override
    public void cleanCacheByKey(String key) {

    }
}
