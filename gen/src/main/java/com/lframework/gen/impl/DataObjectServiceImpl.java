package com.lframework.gen.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.exceptions.impl.DefaultSysException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.gen.dto.dataobj.DataObjectDto;
import com.lframework.gen.dto.dataobj.DataObjectGenerateDto;
import com.lframework.gen.dto.dataobj.GenCreateColumnConfigDto;
import com.lframework.gen.dto.dataobj.GenDataObjectColumnDto;
import com.lframework.gen.dto.dataobj.GenDetailColumnConfigDto;
import com.lframework.gen.dto.dataobj.GenGenerateInfoDto;
import com.lframework.gen.dto.dataobj.GenQueryColumnConfigDto;
import com.lframework.gen.dto.dataobj.GenQueryParamsColumnConfigDto;
import com.lframework.gen.dto.dataobj.GenUpdateColumnConfigDto;
import com.lframework.gen.entity.GenDataObject;
import com.lframework.gen.enums.DataObjectGenStatus;
import com.lframework.gen.enums.DataObjectType;
import com.lframework.gen.events.DataObjectDeleteEvent;
import com.lframework.gen.mappers.GenDataObjectMapper;
import com.lframework.gen.service.IDataObjectColumnService;
import com.lframework.gen.service.IDataObjectService;
import com.lframework.gen.service.IGenCreateColumnConfigService;
import com.lframework.gen.service.IGenDetailColumnConfigService;
import com.lframework.gen.service.IGenQueryColumnConfigService;
import com.lframework.gen.service.IGenQueryParamsColumnConfigService;
import com.lframework.gen.service.IGenUpdateColumnConfigService;
import com.lframework.gen.service.IGenerateInfoService;
import com.lframework.gen.vo.dataobj.CreateDataObjectVo;
import com.lframework.gen.vo.dataobj.QueryDataObjectVo;
import com.lframework.gen.vo.dataobj.UpdateDataObjectGenerateVo;
import com.lframework.gen.vo.dataobj.UpdateDataObjectVo;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.EnumUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataObjectServiceImpl extends BaseMpServiceImpl<GenDataObjectMapper, GenDataObject>
    implements IDataObjectService {

  @Autowired
  private IDataObjectColumnService dataObjectColumnService;

  @Autowired
  private IGenerateInfoService generateInfoService;

  @Autowired
  private IGenCreateColumnConfigService genCreateColumnConfigService;

  @Autowired
  private IGenUpdateColumnConfigService genUpdateColumnConfigService;

  @Autowired
  private IGenQueryColumnConfigService genQueryColumnConfigService;

  @Autowired
  private IGenQueryParamsColumnConfigService genQueryParamsColumnConfigService;

  @Autowired
  private IGenDetailColumnConfigService genDetailColumnConfigService;

  @Override
  public PageResult<DataObjectDto> query(QueryDataObjectVo vo) {

    PageHelperUtil.startPage(vo);

    List<DataObjectDto> datas = getBaseMapper().query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public DataObjectDto findById(@NonNull String id) {

    return getBaseMapper().findById(id);
  }

  @Transactional
  @Override
  public String create(@NonNull CreateDataObjectVo vo) {

    GenDataObject data = new GenDataObject();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());
    data.setType(EnumUtil.getByCode(DataObjectType.class, vo.getType()));
    if (StringUtil.isNotBlank(vo.getDescription())) {
      data.setDescription(vo.getDescription());
    }

    getBaseMapper().insert(data);

    return data.getId();
  }

  @Transactional
  @Override
  public void update(@NonNull UpdateDataObjectVo vo) {

    GenDataObject data = new GenDataObject();
    data.setId(vo.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());
    data.setAvailable(vo.getAvailable());
    data.setDescription(
        StringUtil.isNotBlank(vo.getDescription()) ? vo.getDescription() : StringPool.EMPTY_STR);

    getBaseMapper().updateById(data);
  }

  @Transactional
  @Override
  public void delete(@NonNull String id) {

    List<GenDataObjectColumnDto> columns = dataObjectColumnService.getByDataObjId(id);
    List<String> columnIds = columns.stream().map(GenDataObjectColumnDto::getId)
        .collect(Collectors.toList());

    getBaseMapper().deleteById(id);

    dataObjectColumnService.deleteByDataObjId(id);

    DataObjectDeleteEvent event = new DataObjectDeleteEvent(this);
    event.setId(id);
    event.setColumnIds(columnIds);

    ApplicationUtil.publishEvent(event);
  }

  @Transactional
  @Override
  public void batchDelete(@NonNull List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    getBaseMapper().deleteBatchIds(ids);
  }

  @Transactional
  @Override
  public void batchEnable(List<String> ids, String userId) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<GenDataObject> wrapper = Wrappers.lambdaUpdate(GenDataObject.class)
        .set(GenDataObject::getAvailable, Boolean.TRUE).set(GenDataObject::getUpdateBy, userId)
        .in(GenDataObject::getId, ids);
    getBaseMapper().update(wrapper);
  }

  @Transactional
  @Override
  public void batchUnable(List<String> ids, String userId) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<GenDataObject> wrapper = Wrappers.lambdaUpdate(GenDataObject.class)
        .set(GenDataObject::getAvailable, Boolean.FALSE).set(GenDataObject::getUpdateBy, userId)
        .in(GenDataObject::getId, ids);
    getBaseMapper().update(wrapper);
  }

  @Override
  public DataObjectGenerateDto getGenerateById(String id) {

    DataObjectGenerateDto result = new DataObjectGenerateDto();

    List<GenDataObjectColumnDto> columns = dataObjectColumnService.getByDataObjId(id);
    result.setColumns(columns);

    GenGenerateInfoDto generateInfo = generateInfoService.getByDataObjId(id);
    result.setGenerateInfo(generateInfo);

    List<GenCreateColumnConfigDto> createColumnConfigDtos = genCreateColumnConfigService.getByDataObjId(
        id);
    result.setCreateConfigs(createColumnConfigDtos);

    List<GenUpdateColumnConfigDto> updateColumnConfigDtos = genUpdateColumnConfigService.getByDataObjId(
        id);
    result.setUpdateConfigs(updateColumnConfigDtos);

    List<GenQueryColumnConfigDto> queryColumnConfigDtos = genQueryColumnConfigService.getByDataObjId(
        id);
    result.setQueryConfigs(queryColumnConfigDtos);

    List<GenQueryParamsColumnConfigDto> queryParamsColumnConfigDtos = genQueryParamsColumnConfigService.getByDataObjId(
        id);
    result.setQueryParamsConfigs(queryParamsColumnConfigDtos);

    List<GenDetailColumnConfigDto> detailColumnConfigDtos = genDetailColumnConfigService.getByDataObjId(
        id);
    result.setDetailConfigs(detailColumnConfigDtos);

    return result;
  }

  @Transactional
  @Override
  public void updateGenerate(UpdateDataObjectGenerateVo vo) {

    dataObjectColumnService.updateGenerate(vo.getId(), vo.getColumns());

    generateInfoService.updateGenerate(vo.getId(), vo.getGenerateInfo());

    genCreateColumnConfigService.updateGenerate(vo.getId(), vo.getCreateConfigs());

    genUpdateColumnConfigService.updateGenerate(vo.getId(), vo.getUpdateConfigs());

    genQueryColumnConfigService.updateGenerate(vo.getId(), vo.getQueryConfigs());

    genQueryParamsColumnConfigService.updateGenerate(vo.getId(), vo.getQueryParamsConfigs());

    genDetailColumnConfigService.updateGenerate(vo.getId(), vo.getDetailConfigs());

    this.setStatus(vo.getId(), DataObjectGenStatus.SET_GEN);
  }

  @Transactional
  @Override
  public void setStatus(String id, DataObjectGenStatus status) {

    DataObjectDto record = this.findById(id);
    if (record == null) {
      throw new DefaultClientException("数据对象不存在！");
    }
    switch (status) {
      case CREATED: {
        Wrapper<GenDataObject> updateWrapper = Wrappers.lambdaUpdate(GenDataObject.class)
            .set(GenDataObject::getGenStatus, status).eq(GenDataObject::getId, id)
            .eq(GenDataObject::getGenStatus, DataObjectGenStatus.CREATED);
        if (getBaseMapper().update(updateWrapper) != 1) {
          throw new DefaultClientException("数据对象【" + record.getName() + "】不允许修改！");
        }
        break;
      }
      case SET_TABLE: {
        Wrapper<GenDataObject> updateWrapper = Wrappers.lambdaUpdate(GenDataObject.class)
            .set(GenDataObject::getGenStatus, status).eq(GenDataObject::getId, id)
            .eq(GenDataObject::getGenStatus, DataObjectGenStatus.CREATED);
        if (getBaseMapper().update(updateWrapper) != 1) {
          throw new DefaultClientException("数据对象【" + record.getName() + "】已经设置数据表，不允许重复设置！");
        }
        break;
      }
      case SET_GEN: {
        Wrapper<GenDataObject> updateWrapper = Wrappers.lambdaUpdate(GenDataObject.class)
            .set(GenDataObject::getGenStatus, status).eq(GenDataObject::getId, id)
            .in(GenDataObject::getGenStatus, DataObjectGenStatus.SET_TABLE,
                DataObjectGenStatus.SET_GEN);
        if (getBaseMapper().update(updateWrapper) != 1) {
          throw new DefaultClientException("数据对象【" + record.getName() + "】尚未设置数据表，请先设置数据表！");
        }
        break;
      }
      default: {
        throw new DefaultSysException("未知的GenStatus");
      }
    }
  }
}
