package com.lframework.starter.gen.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.gen.entity.GenDataObj;
import com.lframework.starter.gen.entity.GenDataObjDetail;
import com.lframework.starter.gen.entity.GenDataObjQueryDetail;
import com.lframework.starter.gen.enums.GenRelaMode;
import com.lframework.starter.gen.enums.GenRelaType;
import com.lframework.starter.gen.mappers.GenDataObjMapper;
import com.lframework.starter.gen.service.IGenDataObjDetailService;
import com.lframework.starter.gen.service.IGenDataObjQueryDetailService;
import com.lframework.starter.gen.service.IGenDataObjService;
import com.lframework.starter.gen.vo.data.obj.CreateGenDataObjVo;
import com.lframework.starter.gen.vo.data.obj.GenDataObjDetailVo;
import com.lframework.starter.gen.vo.data.obj.GenDataObjQueryDetailVo;
import com.lframework.starter.gen.vo.data.obj.QueryGenDataObjVo;
import com.lframework.starter.gen.vo.data.obj.UpdateGenDataObjVo;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import java.util.List;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenDataObjServiceImpl extends
    BaseMpServiceImpl<GenDataObjMapper, GenDataObj> implements IGenDataObjService {

  @Autowired
  private IGenDataObjDetailService genDataObjDetailService;

  @Autowired
  private IGenDataObjQueryDetailService genDataObjQueryDetailService;

  @Override
  public PageResult<GenDataObj> query(Integer pageIndex, Integer pageSize, QueryGenDataObjVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<GenDataObj> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<GenDataObj> query(QueryGenDataObjVo vo) {
    return getBaseMapper().query(vo);
  }

  @Override
  public GenDataObj findById(String id) {
    return getBaseMapper().selectById(id);
  }

  @Transactional
  @Override
  public String create(CreateGenDataObjVo data) {

    GenDataObj record = new GenDataObj();
    record.setId(IdUtil.getId());
    record.setName(data.getName());
    if (!StringUtil.isBlank(data.getCategoryId())) {
      record.setCategoryId(data.getCategoryId());
    }
    record.setMainTableId(data.getMainTableId());
    record.setMainTableAlias(data.getMainTableAlias());
    record.setAvailable(Boolean.TRUE);
    record.setDescription(
        StringUtil.isBlank(data.getDescription()) ? StringPool.EMPTY_STR : data.getDescription());

    this.save(record);

    int orderNo = 1;
    if (!CollectionUtil.isEmpty(data.getColumns())) {
      for (GenDataObjDetailVo column : data.getColumns()) {
        GenDataObjDetail detail = new GenDataObjDetail();
        detail.setId(IdUtil.getId());
        detail.setDataObjId(record.getId());
        detail.setMainTableDetailIds(
            CollectionUtil.join(column.getMainTableDetailIds(), StringPool.STR_SPLIT));
        detail.setRelaType(EnumUtil.getByCode(GenRelaType.class, column.getRelaType()));
        detail.setRelaMode(EnumUtil.getByCode(GenRelaMode.class, column.getRelaMode()));
        detail.setSubTableId(column.getSubTableId());
        detail.setSubTableAlias(column.getSubTableAlias());
        detail.setSubTableDetailIds(
            CollectionUtil.join(column.getSubTableDetailIds(), StringPool.STR_SPLIT));
        detail.setOrderNo(orderNo);

        genDataObjDetailService.save(detail);

        orderNo++;
      }
    }

    if (!CollectionUtil.isEmpty(data.getQueryColumns())) {
      orderNo = 1;
      for (GenDataObjQueryDetailVo queryColumn : data.getQueryColumns()) {
        GenDataObjQueryDetail detail = new GenDataObjQueryDetail();
        detail.setId(IdUtil.getId());
        detail.setDataObjId(record.getId());
        detail.setCustomName(queryColumn.getCustomName());
        detail.setCustomSql(queryColumn.getCustomSql());
        detail.setCustomAlias(queryColumn.getCustomAlias());
        detail.setOrderNo(orderNo);

        genDataObjQueryDetailService.save(detail);

        orderNo++;
      }
    }

    return record.getId();
  }

  @Transactional
  @Override
  public void update(UpdateGenDataObjVo data) {

    GenDataObj record = this.getById(data.getId());
    if (record == null) {
      throw new DefaultClientException("数据对象不存在！");
    }

    Wrapper<GenDataObj> updateWrapper = Wrappers.lambdaUpdate(GenDataObj.class)
        .eq(GenDataObj::getId, record.getId()).set(GenDataObj::getName, data.getName())
        .set(GenDataObj::getMainTableAlias, data.getMainTableAlias()).set(GenDataObj::getCategoryId,
            StringUtil.isBlank(data.getCategoryId()) ? null : data.getCategoryId())
        .set(GenDataObj::getDescription,
            StringUtil.isBlank(data.getDescription()) ? StringPool.EMPTY_STR
                : data.getDescription()).set(GenDataObj::getAvailable, data.getAvailable());
    this.update(updateWrapper);

    Wrapper<GenDataObjDetail> deleteDetailWrapper = Wrappers.lambdaQuery(GenDataObjDetail.class)
        .eq(GenDataObjDetail::getDataObjId, record.getId());
    genDataObjDetailService.remove(deleteDetailWrapper);

    Wrapper<GenDataObjQueryDetail> deleteQueryDetailWrapper = Wrappers.lambdaQuery(
        GenDataObjQueryDetail.class).eq(GenDataObjQueryDetail::getDataObjId, record.getId());
    genDataObjQueryDetailService.remove(deleteQueryDetailWrapper);

    int orderNo = 1;
    if (!CollectionUtil.isEmpty(data.getColumns())) {
      for (GenDataObjDetailVo column : data.getColumns()) {
        GenDataObjDetail detail = new GenDataObjDetail();
        detail.setId(column.getId());
        detail.setDataObjId(record.getId());
        detail.setMainTableDetailIds(
            CollectionUtil.join(column.getMainTableDetailIds(), StringPool.STR_SPLIT));
        detail.setRelaType(EnumUtil.getByCode(GenRelaType.class, column.getRelaType()));
        detail.setRelaMode(EnumUtil.getByCode(GenRelaMode.class, column.getRelaMode()));
        detail.setSubTableId(column.getSubTableId());
        detail.setSubTableAlias(column.getSubTableAlias());
        detail.setSubTableDetailIds(
            CollectionUtil.join(column.getSubTableDetailIds(), StringPool.STR_SPLIT));
        detail.setOrderNo(orderNo);

        genDataObjDetailService.save(detail);

        orderNo++;
      }
    }

    if (!CollectionUtil.isEmpty(data.getQueryColumns())) {
      orderNo = 1;
      for (GenDataObjQueryDetailVo queryColumn : data.getQueryColumns()) {
        GenDataObjQueryDetail detail = new GenDataObjQueryDetail();
        detail.setId(queryColumn.getId());
        detail.setDataObjId(record.getId());
        detail.setCustomName(queryColumn.getCustomName());
        detail.setCustomSql(queryColumn.getCustomSql());
        detail.setCustomAlias(queryColumn.getCustomAlias());
        detail.setOrderNo(orderNo);

        genDataObjQueryDetailService.save(detail);

        orderNo++;
      }
    }
  }

  @Transactional
  @Override
  public void delete(@NonNull String id) {

    getBaseMapper().deleteById(id);

    genDataObjDetailService.deleteByObjId(id);

    genDataObjQueryDetailService.deleteByObjId(id);
  }

  @Transactional
  @Override
  public void batchDelete(@NonNull List<String> ids) {

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

    Wrapper<GenDataObj> wrapper = Wrappers.lambdaUpdate(GenDataObj.class)
        .set(GenDataObj::getAvailable, Boolean.TRUE).in(GenDataObj::getId, ids);
    getBaseMapper().update(wrapper);
  }

  @Transactional
  @Override
  public void batchUnable(List<String> ids) {
    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<GenDataObj> wrapper = Wrappers.lambdaUpdate(GenDataObj.class)
        .set(GenDataObj::getAvailable, Boolean.FALSE).in(GenDataObj::getId, ids);
    getBaseMapper().update(wrapper);
  }
}
