package com.lframework.starter.web.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.annotations.oplog.OpLog;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.inner.components.oplog.SystemOpLogType;
import com.lframework.starter.web.inner.entity.SysDept;
import com.lframework.starter.web.inner.enums.system.SysDeptNodeType;
import com.lframework.starter.web.inner.mappers.system.SysDeptMapper;
import com.lframework.starter.web.inner.service.RecursionMappingService;
import com.lframework.starter.web.inner.service.system.SysDeptService;
import com.lframework.starter.web.core.utils.OpLogUtil;
import com.lframework.starter.web.inner.vo.system.dept.CreateSysDeptVo;
import com.lframework.starter.web.inner.vo.system.dept.SysDeptSelectorVo;
import com.lframework.starter.web.inner.vo.system.dept.UpdateSysDeptVo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysDeptServiceImpl extends BaseMpServiceImpl<SysDeptMapper, SysDept> implements
    SysDeptService {

  @Autowired
  private RecursionMappingService recursionMappingService;

  @Override
  public List<SysDept> selector(SysDeptSelectorVo vo) {

    return this.doSelector(vo);
  }

  @Cacheable(value = SysDept.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public SysDept findById(String id) {

    return this.doGetById(id);
  }

  @Override
  public SysDept findByCode(String code) {
    Wrapper<SysDept> queryWrapper = Wrappers.lambdaQuery(SysDept.class).eq(SysDept::getCode, code);
    return getOne(queryWrapper);
  }

  @OpLog(type = SystemOpLogType.class, name = "停用部门，ID：{}", params = "#id")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void unable(String id) {

    List<String> batchIds = new ArrayList<>();
    batchIds.add(id);
    List<String> nodeChildIds = recursionMappingService.getNodeChildIds(id,
        SysDeptNodeType.class);
    if (CollectionUtil.isNotEmpty(nodeChildIds)) {
      batchIds.addAll(nodeChildIds);
    }

    this.doBatchUnable(batchIds);
  }

  @OpLog(type = SystemOpLogType.class, name = "启用部门，ID：{}", params = "#id")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void enable(String id) {

    List<String> batchIds = new ArrayList<>();
    batchIds.add(id);
    List<String> nodeChildIds = recursionMappingService.getNodeParentIds(id,
        SysDeptNodeType.class);
    if (CollectionUtil.isNotEmpty(nodeChildIds)) {
      batchIds.addAll(nodeChildIds);
    }

    this.doBatchEnable(batchIds);
  }

  @OpLog(type = SystemOpLogType.class, name = "新增部门，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateSysDeptVo vo) {

    SysDept data = this.doCreate(vo);

    this.saveRecursion(data.getId(), data.getParentId());

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = SystemOpLogType.class, name = "修改部门，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateSysDeptVo vo) {

    this.doUpdate(vo);

    this.saveRecursion(vo.getId(), vo.getParentId());

    OpLogUtil.setVariable("id", vo.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  protected List<SysDept> doSelector(SysDeptSelectorVo vo) {

    return getBaseMapper().selector(vo);
  }

  protected SysDept doGetById(String id) {

    return getBaseMapper().findById(id);
  }

  protected void doBatchUnable(Collection<String> ids) {

    Wrapper<SysDept> updateWrapper = Wrappers.lambdaUpdate(SysDept.class)
        .set(SysDept::getAvailable, Boolean.FALSE).in(SysDept::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  protected void doBatchEnable(Collection<String> ids) {

    Wrapper<SysDept> updateWrapper = Wrappers.lambdaUpdate(SysDept.class)
        .set(SysDept::getAvailable, Boolean.TRUE).in(SysDept::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  protected SysDept doCreate(CreateSysDeptVo vo) {

    //查询Code是否重复
    Wrapper<SysDept> checkWrapper = Wrappers.lambdaQuery(SysDept.class)
        .eq(SysDept::getCode, vo.getCode());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    //查询Name是否重复
    checkWrapper = Wrappers.lambdaQuery(SysDept.class)
        .eq(SysDept::getName, vo.getName());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    SysDept parentDept = null;
    //如果parentId不为空，查询上级部门是否存在
    if (!StringUtil.isBlank(vo.getParentId())) {
      parentDept = this.getById(vo.getParentId());
      if (parentDept == null) {
        throw new DefaultClientException("上级部门不存在，请检查！");
      }
    }

    SysDept data = new SysDept();
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

    SysDept data = this.findById(vo.getId());
    if (data == null) {
      throw new DefaultClientException("部门不存在！");
    }

    //查询Code是否重复
    Wrapper<SysDept> checkWrapper = Wrappers.lambdaQuery(SysDept.class)
        .eq(SysDept::getCode, vo.getCode()).ne(SysDept::getId, data.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    //查询Name是否重复
    checkWrapper = Wrappers.lambdaQuery(SysDept.class)
        .eq(SysDept::getName, vo.getName())
        .ne(SysDept::getId, data.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    //如果parentId不为空，查询上级部门是否存在
    if (!StringUtil.isBlank(vo.getParentId())) {
      if (ObjectUtil.equals(vo.getParentId(), data.getId())) {
        throw new DefaultClientException("上级部门不能是当前部门！");
      }
      Wrapper<SysDept> checkParentWrapper = Wrappers.lambdaQuery(SysDept.class)
          .eq(SysDept::getId, vo.getParentId());
      if (getBaseMapper().selectCount(checkParentWrapper) == 0) {
        throw new DefaultClientException("上级部门不存在，请检查！");
      }
    }

    Wrapper<SysDept> updateWrapper = Wrappers.lambdaUpdate(SysDept.class)
        .set(SysDept::getCode, vo.getCode()).set(SysDept::getName, vo.getName())
        .set(SysDept::getShortName, vo.getShortName())
        .set(SysDept::getParentId,
            StringUtil.isBlank(vo.getParentId()) ? null : vo.getParentId())
        .set(SysDept::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .set(SysDept::getAvailable, vo.getAvailable()).eq(SysDept::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    if (vo.getAvailable()) {
      this.enable(vo.getId());
    } else {
      this.unable(vo.getId());
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
          SysDeptNodeType.class);
      if (CollectionUtil.isEmpty(parentIds)) {
        parentIds = new ArrayList<>();
      }
      parentIds.add(parentId);

      recursionMappingService.saveNode(deptId, SysDeptNodeType.class,
          parentIds);
    } else {
      recursionMappingService.saveNode(deptId, SysDeptNodeType.class);
    }
  }

  @CacheEvict(value = {SysDept.CACHE_NAME}, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
