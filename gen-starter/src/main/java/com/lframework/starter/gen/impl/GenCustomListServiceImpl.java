package com.lframework.starter.gen.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.gen.components.custom.list.CustomListConfig;
import com.lframework.starter.gen.entity.GenCustomList;
import com.lframework.starter.gen.entity.GenCustomListDetail;
import com.lframework.starter.gen.entity.GenCustomListQueryParams;
import com.lframework.starter.gen.enums.GenCustomListDetailType;
import com.lframework.starter.gen.enums.GenQueryType;
import com.lframework.starter.gen.enums.GenQueryWidthType;
import com.lframework.starter.gen.mappers.GenCustomListMapper;
import com.lframework.starter.gen.service.IGenCustomListDetailService;
import com.lframework.starter.gen.service.IGenCustomListQueryParamsService;
import com.lframework.starter.gen.service.IGenCustomListService;
import com.lframework.starter.gen.vo.custom.list.CreateGenCustomListVo;
import com.lframework.starter.gen.vo.custom.list.GenCustomLisDetailVo;
import com.lframework.starter.gen.vo.custom.list.GenCustomListQueryParamsVo;
import com.lframework.starter.gen.vo.custom.list.QueryGenCustomListVo;
import com.lframework.starter.gen.vo.custom.list.UpdateGenCustomListVo;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenCustomListServiceImpl extends
    BaseMpServiceImpl<GenCustomListMapper, GenCustomList> implements IGenCustomListService {

  @Autowired
  private IGenCustomListQueryParamsService genCustomListQueryParamsService;

  @Autowired
  private IGenCustomListDetailService genCustomListDetailService;

  @Override
  public PageResult<GenCustomList> query(Integer pageIndex, Integer pageSize,
      QueryGenCustomListVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<GenCustomList> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<GenCustomList> query(QueryGenCustomListVo vo) {
    return getBaseMapper().query(vo);
  }

  @Cacheable(value = GenCustomList.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public GenCustomList findById(String id) {
    return getBaseMapper().selectById(id);
  }

  @Transactional
  @Override
  public String create(CreateGenCustomListVo data) {
    GenCustomList record = new GenCustomList();
    record.setId(IdUtil.getId());
    record.setName(data.getName());
    if (!StringUtil.isBlank(data.getCategoryId())) {
      record.setCategoryId(data.getCategoryId());
    }
    record.setQueryPrefixSql(StringUtil.isBlank(data.getQueryPrefixSql()) ? StringPool.EMPTY_STR
        : data.getQueryPrefixSql());
    record.setQuerySuffixSql(StringUtil.isBlank(data.getQuerySuffixSql()) ? StringPool.EMPTY_STR
        : data.getQuerySuffixSql());
    record.setSuffixSql(
        StringUtil.isBlank(data.getSuffixSql()) ? StringPool.EMPTY_STR : data.getSuffixSql());

    record.setLabelWidth(data.getLabelWidth());
    record.setHasPage(data.getHasPage());
    record.setTreeData(data.getTreeData());
    // 如果是树形列表，那么分页必须禁用
    // 如果分页没有禁用，那么一定不是树形列表
    if (record.getTreeData()) {
      record.setHasPage(Boolean.FALSE);
    }

    if (record.getHasPage()) {
      record.setTreeData(Boolean.FALSE);
    }

    if (record.getTreeData()) {
      if (StringUtil.isBlank(data.getTreeIdColumn())) {
        throw new DefaultClientException("ID字段不能为空！");
      }

      if (StringUtil.isBlank(data.getTreeIdColumnRelaId())) {
        throw new DefaultClientException("ID字段不能为空！");
      }

      if (StringUtil.isBlank(data.getTreePidColumn())) {
        throw new DefaultClientException("父级ID字段不能为空！");
      }

      if (StringUtil.isBlank(data.getTreePidColumnRelaId())) {
        throw new DefaultClientException("父级ID字段不能为空！");
      }

      if (StringUtil.isBlank(data.getTreeNodeColumn())) {
        throw new DefaultClientException("树形节点字段不能为空！");
      }

      if (StringUtil.isBlank(data.getTreeNodeColumnRelaId())) {
        throw new DefaultClientException("树形节点字段不能为空！");
      }

      if (StringUtil.isBlank(data.getTreeChildrenKey())) {
        throw new DefaultClientException("子节点Key值不能为空！");
      }

      record.setTreeIdColumn(data.getTreeIdColumn());
      record.setTreeIdColumnRelaId(data.getTreeIdColumnRelaId());
      record.setTreePidColumn(data.getTreePidColumn());
      record.setTreePidColumnRelaId(data.getTreePidColumnRelaId());
      record.setTreeNodeColumn(data.getTreeNodeColumn());
      record.setTreeNodeColumnRelaId(data.getTreeNodeColumnRelaId());
      record.setTreeChildrenKey(data.getTreeChildrenKey());
    }
    record.setDataObjId(data.getDataObjId());
    record.setAvailable(Boolean.TRUE);
    record.setDescription(
        StringUtil.isBlank(data.getDescription()) ? StringPool.EMPTY_STR : data.getDescription());

    if (!CollectionUtil.isEmpty(data.getQueryParams())) {
      int orderNo = 1;
      for (GenCustomListQueryParamsVo queryParam : data.getQueryParams()) {
        GenCustomListQueryParams genCustomListQueryParams = new GenCustomListQueryParams();
        genCustomListQueryParams.setId(IdUtil.getId());
        genCustomListQueryParams.setCustomListId(record.getId());
        genCustomListQueryParams.setRelaId(queryParam.getRelaId());
        genCustomListQueryParams.setDataEntityId(queryParam.getId());
        genCustomListQueryParams.setQueryType(
            EnumUtil.getByCode(GenQueryType.class, queryParam.getQueryType()));
        genCustomListQueryParams.setFormWidth(queryParam.getFormWidth());
        genCustomListQueryParams.setDefaultValue(queryParam.getDefaultValue());
        genCustomListQueryParams.setOrderNo(orderNo);
        genCustomListQueryParams.setType(
            EnumUtil.getByCode(GenCustomListDetailType.class, queryParam.getType()));

        genCustomListQueryParamsService.save(genCustomListQueryParams);

        orderNo++;
      }
    }

    int orderNo = 1;
    for (GenCustomLisDetailVo detail : data.getDetails()) {
      GenCustomListDetail genCustomListDetail = new GenCustomListDetail();
      genCustomListDetail.setId(IdUtil.getId());
      genCustomListDetail.setCustomListId(record.getId());
      genCustomListDetail.setDataEntityId(detail.getId());
      genCustomListDetail.setRelaId(detail.getRelaId());
      genCustomListDetail.setWidthType(
          EnumUtil.getByCode(GenQueryWidthType.class, detail.getWidthType()));
      genCustomListDetail.setWidth(detail.getWidth());
      genCustomListDetail.setSortable(detail.getSortable());
      genCustomListDetail.setOrderNo(orderNo);
      genCustomListDetail.setType(
          EnumUtil.getByCode(GenCustomListDetailType.class, detail.getType()));
      if (genCustomListDetail.getType() == GenCustomListDetailType.CUSTOM) {
        genCustomListDetail.setDataEntityId(null);
      }

      genCustomListDetailService.save(genCustomListDetail);

      orderNo++;
    }

    this.save(record);

    return record.getId();
  }

  @Transactional
  @Override
  public void update(UpdateGenCustomListVo data) {
    GenCustomList record = this.getById(data.getId());
    if (record == null) {
      throw new DefaultClientException("自定义列表不存在！");
    }

    record.setHasPage(data.getHasPage());
    record.setTreeData(data.getTreeData());

    // 如果是树形列表，那么分页必须禁用
    // 如果分页没有禁用，那么一定不是树形列表
    if (record.getTreeData()) {
      record.setHasPage(Boolean.FALSE);
    }

    if (record.getHasPage()) {
      record.setTreeData(Boolean.FALSE);
    }

    if (record.getTreeData()) {
      if (StringUtil.isBlank(data.getTreeIdColumn())) {
        throw new DefaultClientException("ID字段不能为空！");
      }

      if (StringUtil.isBlank(data.getTreeIdColumnRelaId())) {
        throw new DefaultClientException("ID字段不能为空！");
      }

      if (StringUtil.isBlank(data.getTreePidColumn())) {
        throw new DefaultClientException("父级ID字段不能为空！");
      }

      if (StringUtil.isBlank(data.getTreePidColumnRelaId())) {
        throw new DefaultClientException("父级ID字段不能为空！");
      }

      if (StringUtil.isBlank(data.getTreeNodeColumn())) {
        throw new DefaultClientException("树形节点字段不能为空！");
      }

      if (StringUtil.isBlank(data.getTreeNodeColumnRelaId())) {
        throw new DefaultClientException("树形节点字段不能为空！");
      }

      if (StringUtil.isBlank(data.getTreeChildrenKey())) {
        throw new DefaultClientException("子节点Key值不能为空！");
      }
    }

    Wrapper<GenCustomList> updateWrapper = Wrappers.lambdaUpdate(GenCustomList.class)
        .eq(GenCustomList::getId, data.getId()).set(GenCustomList::getName, data.getName())
        .set(GenCustomList::getCategoryId,
            StringUtil.isBlank(data.getCategoryId()) ? null : data.getCategoryId())
        .set(GenCustomList::getLabelWidth, data.getLabelWidth())
        .set(GenCustomList::getHasPage, record.getHasPage())
        .set(GenCustomList::getTreeData, record.getTreeData())
        .set(GenCustomList::getTreeIdColumn, record.getTreeData() ? data.getTreeIdColumn() : null)
        .set(GenCustomList::getTreeIdColumnRelaId, record.getTreeData() ? data.getTreeIdColumnRelaId() : null)
        .set(GenCustomList::getTreePidColumn, record.getTreeData() ? data.getTreePidColumn() : null)
        .set(GenCustomList::getTreePidColumnRelaId, record.getTreeData() ? data.getTreePidColumnRelaId() : null)
        .set(GenCustomList::getTreeNodeColumn,
            record.getTreeData() ? data.getTreeNodeColumn() : null)
        .set(GenCustomList::getTreeNodeColumnRelaId,
            record.getTreeData() ? data.getTreeNodeColumnRelaId() : null)
        .set(GenCustomList::getTreeChildrenKey,
            record.getTreeData() ? data.getTreeChildrenKey() : null)
        .set(GenCustomList::getQueryPrefixSql,
            StringUtil.isBlank(data.getQueryPrefixSql()) ? StringPool.EMPTY_STR
                : data.getQueryPrefixSql())
        .set(GenCustomList::getQuerySuffixSql,
            StringUtil.isBlank(data.getQuerySuffixSql()) ? StringPool.EMPTY_STR
                : data.getQuerySuffixSql())
        .set(GenCustomList::getSuffixSql,
            StringUtil.isBlank(data.getSuffixSql()) ? StringPool.EMPTY_STR : data.getSuffixSql())
        .set(GenCustomList::getDescription,
            StringUtil.isBlank(data.getDescription()) ? StringPool.EMPTY_STR
                : data.getDescription()).set(GenCustomList::getAvailable, data.getAvailable());

    this.update(updateWrapper);

    genCustomListDetailService.deleteByCustomListId(data.getId());
    genCustomListQueryParamsService.deleteByCustomListId(data.getId());

    if (!CollectionUtil.isEmpty(data.getQueryParams())) {
      int orderNo = 1;
      for (GenCustomListQueryParamsVo queryParam : data.getQueryParams()) {
        GenCustomListQueryParams genCustomListQueryParams = new GenCustomListQueryParams();
        genCustomListQueryParams.setId(IdUtil.getId());
        genCustomListQueryParams.setCustomListId(record.getId());
        genCustomListQueryParams.setRelaId(queryParam.getRelaId());
        genCustomListQueryParams.setDataEntityId(queryParam.getId());
        genCustomListQueryParams.setQueryType(
            EnumUtil.getByCode(GenQueryType.class, queryParam.getQueryType()));
        genCustomListQueryParams.setFormWidth(queryParam.getFormWidth());
        genCustomListQueryParams.setDefaultValue(queryParam.getDefaultValue());
        genCustomListQueryParams.setOrderNo(orderNo);
        genCustomListQueryParams.setType(
            EnumUtil.getByCode(GenCustomListDetailType.class, queryParam.getType()));

        genCustomListQueryParamsService.save(genCustomListQueryParams);

        orderNo++;
      }
    }

    int orderNo = 1;
    for (GenCustomLisDetailVo detail : data.getDetails()) {
      GenCustomListDetail genCustomListDetail = new GenCustomListDetail();
      genCustomListDetail.setId(IdUtil.getId());
      genCustomListDetail.setCustomListId(record.getId());
      genCustomListDetail.setDataEntityId(detail.getId());
      genCustomListDetail.setRelaId(detail.getRelaId());
      genCustomListDetail.setWidthType(
          EnumUtil.getByCode(GenQueryWidthType.class, detail.getWidthType()));
      genCustomListDetail.setWidth(detail.getWidth());
      genCustomListDetail.setSortable(detail.getSortable());
      genCustomListDetail.setOrderNo(orderNo);
      genCustomListDetail.setType(
          EnumUtil.getByCode(GenCustomListDetailType.class, detail.getType()));
      if (genCustomListDetail.getType() == GenCustomListDetailType.CUSTOM) {
        genCustomListDetail.setDataEntityId(null);
      }

      genCustomListDetailService.save(genCustomListDetail);

      orderNo++;
    }
  }

  @Transactional
  @Override
  public void delete(String id) {
    this.removeById(id);
    genCustomListDetailService.deleteByCustomListId(id);
    genCustomListQueryParamsService.deleteByCustomListId(id);
  }

  @Transactional
  @Override
  public void batchDelete(List<String> ids) {
    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    for (String id : ids) {
      this.delete(id);
    }
  }

  @Transactional
  @Override
  public void batchEnable(List<String> ids) {
    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<GenCustomList> wrapper = Wrappers.lambdaUpdate(GenCustomList.class)
        .set(GenCustomList::getAvailable, Boolean.TRUE).in(GenCustomList::getId, ids);
    getBaseMapper().update(wrapper);
  }

  @Transactional
  @Override
  public void batchUnable(List<String> ids) {
    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<GenCustomList> wrapper = Wrappers.lambdaUpdate(GenCustomList.class)
        .set(GenCustomList::getAvailable, Boolean.FALSE).in(GenCustomList::getId, ids);
    getBaseMapper().update(wrapper);
  }

  @Override
  public List<String> getRelaGenDataObjIds(String objId) {
    return getBaseMapper().getRelaGenDataObjIds(objId);
  }

  @Override
  public List<String> getRelaGenDataEntityIds(String entityId) {
    return getBaseMapper().getRelaGenDataEntityIds(entityId);
  }

  @CacheEvict(value = {GenCustomList.CACHE_NAME, CustomListConfig.CACHE_NAME}, key = "#key")
  @Override
  public void cleanCacheByKey(Serializable key) {
    super.cleanCacheByKey(key);
  }
}
