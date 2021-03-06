package com.lframework.gen.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.gen.converters.GenViewTypeConverter;
import com.lframework.gen.dto.dataobj.GenDataObjectColumnDto;
import com.lframework.gen.entity.GenDataObjectColumn;
import com.lframework.gen.enums.GenDataType;
import com.lframework.gen.enums.GenOrderType;
import com.lframework.gen.enums.GenViewType;
import com.lframework.gen.mappers.GenDataObjectColumnMapper;
import com.lframework.gen.service.IDataObjectColumnService;
import com.lframework.gen.vo.dataobj.CreateDataObjectColumnVo;
import com.lframework.gen.vo.dataobj.UpdateDataObjectColumnGenerateVo;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import java.util.List;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataObjectColumnServiceImpl extends
    BaseMpServiceImpl<GenDataObjectColumnMapper, GenDataObjectColumn>
    implements IDataObjectColumnService {

  @Autowired
  private GenViewTypeConverter genViewTypeConverter;

  @Transactional
  @Override
  public String create(@NonNull CreateDataObjectColumnVo vo) {

    GenDataObjectColumn data = new GenDataObjectColumn();
    data.setId(vo.getId() == null ? IdUtil.getId() : vo.getId());
    data.setDataObjId(vo.getDataObjId());
    data.setName(vo.getName());
    data.setColumnName(vo.getPropertyName());
    data.setIsKey(vo.getIsKey());
    data.setDataType(EnumUtil.getByCode(GenDataType.class, vo.getDataType()));
    data.setColumnOrder(vo.getColumnOrder());
    data.setDescription(vo.getDescription());
    List<GenViewType> viewTypes = genViewTypeConverter.convert(data.getDataType());
    if (CollectionUtil.isEmpty(viewTypes)) {
      throw new DefaultClientException("?????????" + data.getColumnName() + "?????????????????????");
    }
    data.setViewType(viewTypes.get(0));

    getBaseMapper().insert(data);

    return data.getId();
  }

  @Transactional
  @Override
  public void deleteByDataObjId(String dataObjId) {

    Wrapper<GenDataObjectColumn> wrapper = Wrappers.lambdaQuery(GenDataObjectColumn.class)
        .eq(GenDataObjectColumn::getDataObjId, dataObjId);
    getBaseMapper().delete(wrapper);
  }

  @Override
  public List<GenDataObjectColumnDto> getByDataObjId(String dataObjId) {

    return getBaseMapper().getByDataObjId(dataObjId);
  }

  @Transactional
  @Override
  public void updateGenerate(String dataObjId, List<UpdateDataObjectColumnGenerateVo> vo) {

    Wrapper<GenDataObjectColumn> queryWrapper = Wrappers.lambdaQuery(GenDataObjectColumn.class)
        .eq(GenDataObjectColumn::getDataObjId, dataObjId)
        .orderByAsc(GenDataObjectColumn::getColumnOrder);
    List<GenDataObjectColumn> columns = getBaseMapper().selectList(queryWrapper);
    if (CollectionUtil.isEmpty(columns)) {
      throw new DefaultClientException("????????????????????????");
    }

    int orderNo = 0;
    for (GenDataObjectColumn column : columns) {
      UpdateDataObjectColumnGenerateVo columnVo = vo.stream()
          .filter(t -> t.getId().equals(column.getId()))
          .findFirst().orElse(null);
      if (columnVo == null) {
        throw new DefaultClientException("?????????" + column.getName() + "??????????????????????????????");
      }

      GenViewType viewType = EnumUtil.getByCode(GenViewType.class, columnVo.getViewType());
      GenDataType dataType = EnumUtil.getByCode(GenDataType.class, columnVo.getDataType());

      if (!genViewTypeConverter.canConvert(viewType, dataType)) {
        throw new DefaultClientException("?????????" + columnVo.getName() + "??????????????????????????????????????????");
      }

      if (!columnVo.getFixEnum()) {
        if (viewType == GenViewType.SELECT && dataType != GenDataType.BOOLEAN) {
          throw new DefaultClientException(
              "?????????" + columnVo.getName() + "?????????????????????" + viewType.getDesc() + "?????????????????????????????????"
                  + GenDataType.BOOLEAN.getDesc() + "?????????????????????????????????????????????");
        }
      }

      if (columnVo.getFixEnum()) {
        if (StringUtil.isBlank(columnVo.getEnumBack())) {
          throw new DefaultClientException("?????????" + columnVo.getName() + "?????????????????????????????????");
        }

        if (StringUtil.isBlank(columnVo.getEnumFront())) {
          throw new DefaultClientException("?????????" + columnVo.getName() + "?????????????????????????????????");
        }
      }

      if (columnVo.getIsOrder()) {
        if (StringUtil.isBlank(columnVo.getOrderType())) {
          throw new DefaultClientException("?????????" + columnVo.getName() + "??????????????????????????????");
        }
      }

      Wrapper<GenDataObjectColumn> updateWrapper = Wrappers.lambdaUpdate(GenDataObjectColumn.class)
          .set(GenDataObjectColumn::getName, columnVo.getName())
          .set(GenDataObjectColumn::getColumnName, columnVo.getColumnName())
          .set(GenDataObjectColumn::getDataType, dataType)
          .set(GenDataObjectColumn::getColumnOrder, orderNo++)
          .set(GenDataObjectColumn::getDescription, columnVo.getDescription())
          .set(GenDataObjectColumn::getViewType, viewType)
          .set(GenDataObjectColumn::getFixEnum, columnVo.getFixEnum())
          .set(GenDataObjectColumn::getEnumBack, columnVo.getEnumBack())
          .set(GenDataObjectColumn::getEnumFront, columnVo.getEnumFront())
          .set(GenDataObjectColumn::getRegularExpression,
              !StringUtil.isBlank(columnVo.getRegularExpression()) ?
                  columnVo.getRegularExpression() :
                  StringPool.EMPTY_STR).set(GenDataObjectColumn::getIsOrder, columnVo.getIsOrder())
          .set(GenDataObjectColumn::getOrderType, columnVo.getIsOrder() ?
              EnumUtil.getByCode(GenOrderType.class, columnVo.getOrderType()) :
              null).set(GenDataObjectColumn::getColumnOrder, vo.indexOf(columnVo))
          .eq(GenDataObjectColumn::getId, columnVo.getId());

      getBaseMapper().update(updateWrapper);
    }
  }
}
