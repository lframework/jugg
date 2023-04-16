package com.lframework.starter.mybatis.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.entity.DefaultSysDept;
import com.lframework.starter.mybatis.enums.DefaultOpLogType;
import com.lframework.starter.mybatis.enums.system.SysDeptNodeType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.mappers.system.DefaultSysDeptMapper;
import com.lframework.starter.mybatis.service.system.RecursionMappingService;
import com.lframework.starter.mybatis.service.system.SysDeptService;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.vo.system.dept.CreateSysDeptVo;
import com.lframework.starter.mybatis.vo.system.dept.UpdateSysDeptVo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.utils.IdUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

public class DefaultSysDeptServiceImpl extends
    BaseMpServiceImpl<DefaultSysDeptMapper, DefaultSysDept>
    implements SysDeptService {

  @Autowired
  private RecursionMappingService recursionMappingService;

  @Override
  public List<DefaultSysDept> selector() {

    return this.doSelector();
  }

  @Cacheable(value = DefaultSysDept.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public DefaultSysDept findById(String id) {

    return this.doGetById(id);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "停用部门，ID：{}", params = "#ids", loopFormat = true)
  @Transactional(rollbackFor = Exception.class)
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

    this.doBatchUnable(batchIds);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "启用部门，ID：{}", params = "#ids", loopFormat = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchEnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    List<String> batchIds = new ArrayList<>();
    for (String id : ids) {
      List<String> nodeChildIds = recursionMappingService.getNodeParentIds(id,
          ApplicationUtil.getBean(SysDeptNodeType.class));
      if (CollectionUtil.isEmpty(nodeChildIds)) {
        continue;
      }

      batchIds.addAll(nodeChildIds);
    }

    batchIds.addAll(ids);

    this.doBatchEnable(batchIds);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "新增部门，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateSysDeptVo vo) {

    DefaultSysDept data = this.doCreate(vo);

    this.saveRecursion(data.getId(), data.getParentId());

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "修改部门，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateSysDeptVo vo) {

    this.doUpdate(vo);

    this.saveRecursion(vo.getId(), vo.getParentId());

    OpLogUtil.setVariable("id", vo.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  protected List<DefaultSysDept> doSelector() {

    return getBaseMapper().selector();
  }

  protected DefaultSysDept doGetById(String id) {

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

    DefaultSysDept parentDept = null;
    //如果parentId不为空，查询上级部门是否存在
    if (!StringUtil.isBlank(vo.getParentId())) {
      parentDept = this.getById(vo.getParentId());
      if (parentDept == null) {
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
    data.setAvailable(parentDept == null ? Boolean.TRUE : parentDept.getAvailable());
    data.setDescription(vo.getDescription());

    getBaseMapper().insert(data);

    return data;
  }

  protected void doUpdate(UpdateSysDeptVo vo) {

    DefaultSysDept data = this.findById(vo.getId());
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

    if (vo.getAvailable()) {
      this.batchEnable(Collections.singletonList(vo.getId()));
    } else {
      this.batchUnable(Collections.singletonList(vo.getId()));
    }
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

  @CacheEvict(value = DefaultSysDept.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
