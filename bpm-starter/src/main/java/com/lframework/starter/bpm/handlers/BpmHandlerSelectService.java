package com.lframework.starter.bpm.handlers;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.inner.entity.SysDept;
import com.lframework.starter.web.inner.entity.SysRole;
import com.lframework.starter.web.inner.entity.SysUser;
import com.lframework.starter.web.inner.entity.SysUserDept;
import com.lframework.starter.web.inner.service.system.SysDeptService;
import com.lframework.starter.web.inner.service.system.SysRoleService;
import com.lframework.starter.web.inner.service.system.SysUserDeptService;
import com.lframework.starter.web.inner.service.system.SysUserService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.dromara.warm.flow.core.utils.CollUtil;
import org.dromara.warm.flow.ui.dto.HandlerFunDto;
import org.dromara.warm.flow.ui.dto.HandlerQuery;
import org.dromara.warm.flow.ui.dto.TreeFunDto;
import org.dromara.warm.flow.ui.service.HandlerSelectService;
import org.dromara.warm.flow.ui.vo.HandlerFeedBackVo;
import org.dromara.warm.flow.ui.vo.HandlerSelectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BpmHandlerSelectService implements HandlerSelectService {

  @Autowired
  private SysRoleService sysRoleService;

  @Autowired
  private SysDeptService sysDeptService;

  @Autowired
  private SysUserService sysUserService;

  @Autowired
  private SysUserDeptService sysUserDeptService;

  @Override
  public List<String> getHandlerType() {
    return Arrays.asList("用户", "角色", "部门");
  }

  @Override
  public HandlerSelectVo getHandlerSelect(HandlerQuery handlerQuery) {
    if ("角色".equals(handlerQuery.getHandlerType())) {
      return getRole(handlerQuery);
    }

    if ("部门".equals(handlerQuery.getHandlerType())) {
      return getDept(handlerQuery);
    }

    if ("用户".equals(handlerQuery.getHandlerType())) {
      return getUser(handlerQuery);
    }

    return new HandlerSelectVo();
  }

  @Override
  public List<HandlerFeedBackVo> handlerFeedback(List<String> storageIds) {
    List<HandlerFeedBackVo> handlerFeedBackVos = new ArrayList<>();
    if (CollUtil.isEmpty(storageIds)) {
      return handlerFeedBackVos;
    }

    for (String storageId : storageIds) {
      if (storageId.startsWith("role:")) {
        String tmpId = storageId.substring(5);
        SysRole role = sysRoleService.findById(tmpId);

        handlerFeedBackVos.add(new HandlerFeedBackVo(storageId, role.getName()));
      } else if (storageId.startsWith("dept:")) {
        String tmpId = storageId.substring(5);
        SysDept dept = sysDeptService.findById(tmpId);
        handlerFeedBackVos.add(new HandlerFeedBackVo(storageId, dept.getName()));
      } else {
        String tmpId = storageId;
        SysUser user = sysUserService.findById(tmpId);
        handlerFeedBackVos.add(new HandlerFeedBackVo(storageId, user.getName()));
      }
    }
    return handlerFeedBackVos;
  }

  /**
   * 获取角色列表
   *
   * @param query 查询条件
   * @return HandlerSelectVo
   */
  private HandlerSelectVo getRole(HandlerQuery query) {
    // 查询角色列表
    LocalDateTime begTime = StringUtil.isBlank(query.getBeginTime()) ? null
        : DateUtil.toLocalDateTime(DateUtil.parseDate(query.getBeginTime()));
    LocalDateTime endTime = StringUtil.isBlank(query.getEndTime()) ? null
        : DateUtil.toLocalDateTimeMax(DateUtil.parseDate(query.getEndTime()));
    Wrapper<SysRole> queryWrapper = Wrappers.lambdaQuery(SysRole.class)
        .eq(StringUtil.isNotBlank(query.getHandlerCode()), SysRole::getCode, query.getHandlerCode())
        .eq(SysRole::getAvailable, Boolean.TRUE)
        .like(StringUtil.isNotBlank(query.getHandlerName()), SysRole::getName,
            query.getHandlerName())
        .ge(begTime != null, SysRole::getCreateTime, begTime)
        .le(endTime != null, SysRole::getCreateTime, endTime)
        .orderByAsc(SysRole::getCode);
    PageHelperUtil.startPage(query.getPageNum(), query.getPageSize());
    List<SysRole> roleList = sysRoleService.list(queryWrapper);
    PageResult<SysRole> pageResult = PageResultUtil.convert(new PageInfo<>(roleList));
    long total = pageResult.getTotalCount();

    // 业务系统数据，转成组件内部能够显示的数据, total是业务数据总数，用于分页显示
    HandlerFunDto<SysRole> handlerFunDto = new HandlerFunDto<>(roleList, total)
        // 以下设置获取内置变量的Function
        .setStorageId(role -> "role:" + role.getId()) // 前面拼接role:  是为了防止用户、角色的主键重复
        .setHandlerCode(SysRole::getCode) // 权限编码
        .setHandlerName(SysRole::getName) // 权限名称
        .setCreateTime(role -> DateUtil.formatDateTime(role.getCreateTime()));

    return getHandlerSelectVo(handlerFunDto);
  }

  /**
   * 获取部门列表
   *
   * @param query 查询条件
   * @return HandlerSelectVo
   */
  private HandlerSelectVo getDept(HandlerQuery query) {
    // 查询部门列表
    LocalDateTime begTime = StringUtil.isBlank(query.getBeginTime()) ? null
        : DateUtil.toLocalDateTime(DateUtil.parseDate(query.getBeginTime()));
    LocalDateTime endTime = StringUtil.isBlank(query.getEndTime()) ? null
        : DateUtil.toLocalDateTimeMax(DateUtil.parseDate(query.getEndTime()));
    Wrapper<SysDept> queryWrapper = Wrappers.lambdaQuery(SysDept.class)
        .eq(StringUtil.isNotBlank(query.getHandlerCode()), SysDept::getCode, query.getHandlerCode())
        .eq(SysDept::getAvailable, Boolean.TRUE)
        .like(StringUtil.isNotBlank(query.getHandlerName()), SysDept::getName,
            query.getHandlerName())
        .ge(begTime != null, SysDept::getCreateTime, begTime)
        .le(endTime != null, SysDept::getCreateTime, endTime)
        .orderByAsc(SysDept::getCode);
    PageHelperUtil.startPage(query.getPageNum(), query.getPageSize());
    List<SysDept> deptList = sysDeptService.list(queryWrapper);
    PageResult<SysDept> pageResult = PageResultUtil.convert(new PageInfo<>(deptList));
    long total = pageResult.getTotalCount();

    // 业务系统数据，转成组件内部能够显示的数据, total是业务数据总数，用于分页显示
    HandlerFunDto<SysDept> handlerFunDto = new HandlerFunDto<>(deptList, total)
        .setStorageId(dept -> "dept:" + dept.getId()) // 前面拼接dept:  是为了防止用户、部门的主键重复
        .setHandlerCode(SysDept::getCode)
        .setHandlerName(SysDept::getName) // 权限名称
        .setCreateTime(dept -> DateUtil.formatDateTime(dept.getCreateTime()));

    return getHandlerSelectVo(handlerFunDto);
  }

  /**
   * 获取用户列表, 同时构建左侧部门树状结构
   *
   * @param query 查询条件
   * @return HandlerSelectVo
   */
  private HandlerSelectVo getUser(HandlerQuery query) {
    // 查询用户列表
    LocalDateTime begTime = StringUtil.isBlank(query.getBeginTime()) ? null
        : DateUtil.toLocalDateTime(DateUtil.parseDate(query.getBeginTime()));
    LocalDateTime endTime = StringUtil.isBlank(query.getEndTime()) ? null
        : DateUtil.toLocalDateTimeMax(DateUtil.parseDate(query.getEndTime()));
    Wrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class)
        .eq(StringUtil.isNotBlank(query.getHandlerCode()), SysUser::getCode, query.getHandlerCode())
        .eq(SysUser::getAvailable, Boolean.TRUE)
        .like(StringUtil.isNotBlank(query.getHandlerName()), SysUser::getName,
            query.getHandlerName())
        .ge(begTime != null, SysUser::getCreateTime, begTime)
        .le(endTime != null, SysUser::getCreateTime, endTime)
        .inSql(StringUtil.isNotBlank(query.getGroupId()), SysUser::getId,
            "SELECT user_id FROM sys_user_dept WHERE dept_id = " + query.getGroupId())
        .orderByAsc(SysUser::getCode);
    PageHelperUtil.startPage(query.getPageNum(), query.getPageSize());
    List<SysUser> userList = sysUserService.list(queryWrapper);
    PageResult<SysUser> pageResult = PageResultUtil.convert(new PageInfo<>(userList));
    long total = pageResult.getTotalCount();

    // 查询部门列表，构建树状结构
    Wrapper<SysDept> queryDeptWrapper = Wrappers.lambdaQuery(SysDept.class)
        .eq(SysDept::getAvailable, Boolean.TRUE)
        .orderByAsc(SysDept::getCode);
    List<SysDept> deptList = sysDeptService.list(queryDeptWrapper);

    // 业务系统数据，转成组件内部能够显示的数据, total是业务数据总数，用于分页显示
    HandlerFunDto<SysUser> handlerFunDto = new HandlerFunDto<>(userList, total)
        .setStorageId(SysUser::getId)
        .setHandlerCode(SysUser::getUsername) // 权限编码
        .setHandlerName(SysUser::getName) // 权限名称
        .setCreateTime(user -> DateUtil.formatDateTime(user.getCreateTime()))
        .setGroupName(user -> {

          List<SysUserDept> userDeptList = sysUserDeptService.getByUserId(user.getId());
          Set<String> deptNameList = userDeptList.stream().map(userDept -> {
            SysDept dept = sysDeptService.findById(userDept.getDeptId());

            return dept.getName();
          }).collect(Collectors.toSet());

          return StringUtil.join(StringPool.STR_SPLIT_CN, deptNameList);
        });

    // 业务系统机构，转成组件内部左侧树列表能够显示的数据
    TreeFunDto<SysDept> treeFunDto = new TreeFunDto<>(deptList)
        .setId(SysDept::getId) // 左侧树ID
        .setName(SysDept::getName) // 左侧树名称
        .setParentId(SysDept::getParentId); // 左侧树父级ID

    return getHandlerSelectVo(handlerFunDto, treeFunDto);
  }
}
