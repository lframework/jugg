package com.lframework.starter.mybatis.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.dto.system.dept.DefaultSysDeptDto;
import com.lframework.starter.mybatis.entity.DefaultSysDept;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.enums.system.SysDeptNodeType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.mappers.system.DefaultSysDeptMapper;
import com.lframework.starter.mybatis.service.system.IRecursionMappingService;
import com.lframework.starter.mybatis.service.system.ISysDeptService;
import com.lframework.starter.mybatis.service.system.ISysUserDeptService;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.vo.system.dept.CreateSysDeptVo;
import com.lframework.starter.mybatis.vo.system.dept.UpdateSysDeptVo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.IdUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

public class DefaultSysDeptServiceImpl extends
    BaseMpServiceImpl<DefaultSysDeptMapper, DefaultSysDept>
    implements ISysDeptService {

  @Autowired
  private IRecursionMappingService recursionMappingService;

  @Autowired
  private ISysUserDeptService sysUserDeptService;

  @Override
  public List<DefaultSysDeptDto> selector() {

    return this.doSelector();
  }

  @Cacheable(value = DefaultSysDeptDto.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public DefaultSysDeptDto findById(String id) {

    return this.doGetById(id);
  }

  @OpLog(type = OpLogType.OTHER, name = "停用部门，ID：{}", params = "#ids", loopFormat = true)
  @Transactional
  @Override
  public void batchUnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    List<String> batchIds = new ArrayList<>();
    for (String id : ids) {
      List<String> nodeChildIds = recursionMappingService.getNodeChildIds(id,
          ApplicationUtil.getBean(SysDeptNodeType.class));
      if (CollectionUtil.isEmpty(nodeChildIds)) {
        continue;
      }

      batchIds.addAll(nodeChildIds);
    }

    batchIds.addAll(ids);

    this.doBatchUnable(ids);
  }

  @OpLog(type = OpLogType.OTHER, name = "启用部门，ID：{}", params = "#ids", loopFormat = true)
  @Transactional
  @Override
  public void batchEnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    List<String> batchIds = new ArrayList<>();
    for (String id : ids) {
      List<String> nodeChildIds = recursionMappingService.getNodeChildIds(id,
          ApplicationUtil.getBean(SysDeptNodeType.class));
      if (CollectionUtil.isEmpty(nodeChildIds)) {
        continue;
      }

      batchIds.addAll(nodeChildIds);
    }

    batchIds.addAll(ids);

    this.doBatchEnable(batchIds);
  }

  @OpLog(type = OpLogType.OTHER, name = "新增部门，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public String create(CreateSysDeptVo vo) {

    if (!StringUtil.isBlank(vo.getParentId())) {
      if (sysUserDeptService.hasByDeptId(vo.getParentId())) {
        throw new DefaultClientException("上级部门不能是已有用户归属的部门！");
      }
    }

    DefaultSysDept data = this.doCreate(vo);

    this.saveRecursion(data.getId(), data.getParentId());

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "修改部门，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public void update(UpdateSysDeptVo vo) {

    if (!StringUtil.isBlank(vo.getParentId())) {
      if (sysUserDeptService.hasByDeptId(vo.getParentId())) {
        throw new DefaultClientException("上级部门不能是已有用户归属的部门！");
      }
    }

    this.doUpdate(vo);

    this.saveRecursion(vo.getId(), vo.getParentId());

    OpLogUtil.setVariable("id", vo.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  protected List<DefaultSysDeptDto> doSelector() {

    return getBaseMapper().selector();
  }

  protected DefaultSysDeptDto doGetById(String id) {

    return getBaseMapper().findById(id);
  }

  protected void doBatchUnable(Collection<String> ids) {

    Wrapper<DefaultSysDept> updateWrapper = Wrappers.lambdaUpdate(DefaultSysDept.class)
        .set(DefaultSysDept::getAvailable, Boolean.FALSE).in(DefaultSysDept::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  protected void doBatchEnable(Collection<String> ids) {

    Wrapper<DefaultSysDept> updateWrapper = Wrappers.lambdaUpdate(DefaultSysDept.class)
        .set(DefaultSysDept::getAvailable, Boolean.TRUE).in(DefaultSysDept::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  protected DefaultSysDept doCreate(CreateSysDeptVo vo) {

    //查询Code是否重复
    Wrapper<DefaultSysDept> checkWrapper = Wrappers.lambdaQuery(DefaultSysDept.class)
        .eq(DefaultSysDept::getCode, vo.getCode());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    //查询Name是否重复
    checkWrapper = Wrappers.lambdaQuery(DefaultSysDept.class)
        .eq(DefaultSysDept::getName, vo.getName());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    //如果parentId不为空，查询上级部门是否存在
    if (!StringUtil.isBlank(vo.getParentId())) {
      Wrapper<DefaultSysDept> checkParentWrapper = Wrappers.lambdaQuery(DefaultSysDept.class)
          .eq(DefaultSysDept::getId, vo.getParentId());
      if (getBaseMapper().selectCount(checkParentWrapper) == 0) {
        throw new DefaultClientException("上级部门不存在，请检查！");
      }
    }

    DefaultSysDept data = new DefaultSysDept();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());
    data.setShortName(vo.getShortName());
    if (!StringUtil.isBlank(vo.getParentId())) {
      data.setParentId(vo.getParentId());
    }
    data.setAvailable(Boolean.TRUE);
    data.setDescription(vo.getDescription());

    getBaseMapper().insert(data);

    return data;
  }

  protected void doUpdate(UpdateSysDeptVo vo) {

    DefaultSysDeptDto data = this.findById(vo.getId());
    if (data == null) {
      throw new DefaultClientException("部门不存在！");
    }

    //查询Code是否重复
    Wrapper<DefaultSysDept> checkWrapper = Wrappers.lambdaQuery(DefaultSysDept.class)
        .eq(DefaultSysDept::getCode, vo.getCode()).ne(DefaultSysDept::getId, data.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    //查询Name是否重复
    checkWrapper = Wrappers.lambdaQuery(DefaultSysDept.class)
        .eq(DefaultSysDept::getName, vo.getName())
        .ne(DefaultSysDept::getId, data.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    //如果parentId不为空，查询上级部门是否存在
    if (!StringUtil.isBlank(vo.getParentId())) {
      if (ObjectUtil.equals(vo.getParentId(), data.getId())) {
        throw new DefaultClientException("上级部门不能是当前部门！");
      }
      Wrapper<DefaultSysDept> checkParentWrapper = Wrappers.lambdaQuery(DefaultSysDept.class)
          .eq(DefaultSysDept::getId, vo.getParentId());
      if (getBaseMapper().selectCount(checkParentWrapper) == 0) {
        throw new DefaultClientException("上级部门不存在，请检查！");
      }
    }

    Wrapper<DefaultSysDept> updateWrapper = Wrappers.lambdaUpdate(DefaultSysDept.class)
        .set(DefaultSysDept::getCode, vo.getCode()).set(DefaultSysDept::getName, vo.getName())
        .set(DefaultSysDept::getShortName, vo.getShortName())
        .set(DefaultSysDept::getParentId,
            StringUtil.isBlank(vo.getParentId()) ? null : vo.getParentId())
        .set(DefaultSysDept::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .set(DefaultSysDept::getAvailable, vo.getAvailable()).eq(DefaultSysDept::getId, vo.getId());

    getBaseMapper().update(updateWrapper);
  }

  /**
   * 保存递归信息
   *
   * @param deptId
   * @param parentId
   */
  protected void saveRecursion(String deptId, String parentId) {

    if (!StringUtil.isBlank(parentId)) {
      List<String> parentIds = recursionMappingService.getNodeParentIds(parentId,
          ApplicationUtil.getBean(SysDeptNodeType.class));
      if (CollectionUtil.isEmpty(parentIds)) {
        parentIds = new ArrayList<>();
      }
      parentIds.add(parentId);

      recursionMappingService.saveNode(deptId, ApplicationUtil.getBean(SysDeptNodeType.class),
          parentIds);
    } else {
      recursionMappingService.saveNode(deptId, ApplicationUtil.getBean(SysDeptNodeType.class));
    }
  }

  @CacheEvict(value = DefaultSysDeptDto.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
