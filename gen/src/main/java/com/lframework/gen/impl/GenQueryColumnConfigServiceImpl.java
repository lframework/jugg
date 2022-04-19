package com.lframework.gen.impl;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.gen.dto.dataobj.GenDataObjectColumnDto;
import com.lframework.gen.dto.dataobj.GenQueryColumnConfigDto;
import com.lframework.gen.entity.GenQueryColumnConfig;
import com.lframework.gen.enums.GenQueryWidthType;
import com.lframework.gen.mappers.GenQueryColumnConfigMapper;
import com.lframework.gen.service.IDataObjectColumnService;
import com.lframework.gen.service.IGenQueryColumnConfigService;
import com.lframework.gen.vo.dataobj.UpdateQueryColumnConfigVo;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.EnumUtil;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenQueryColumnConfigServiceImpl extends
    BaseMpServiceImpl<GenQueryColumnConfigMapper, GenQueryColumnConfig> implements
    IGenQueryColumnConfigService {

  @Autowired
  private IDataObjectColumnService dataObjectColumnService;

  @Override
  public List<GenQueryColumnConfigDto> getByDataObjId(String dataObjId) {

    List<GenDataObjectColumnDto> columns = dataObjectColumnService.getByDataObjId(dataObjId);
    if (CollectionUtil.isEmpty(columns)) {
      return Collections.EMPTY_LIST;
    }

    return getBaseMapper()
        .getByIds(columns.stream().map(GenDataObjectColumnDto::getId).collect(Collectors.toList()));
  }

  @Transactional
  @Override
  public void updateGenerate(String dataObjId, List<UpdateQueryColumnConfigVo> vo) {

    List<GenDataObjectColumnDto> columns = dataObjectColumnService.getByDataObjId(dataObjId);
    if (!CollectionUtil.isEmpty(columns)) {
      getBaseMapper()
          .deleteBatchIds(
              columns.stream().map(GenDataObjectColumnDto::getId).collect(Collectors.toList()));
    }

    if (!CollectionUtil.isEmpty(vo)) {
      int orderNo = 1;
      for (UpdateQueryColumnConfigVo updateQueryColumnConfigVo : vo) {
        GenQueryColumnConfig record = new GenQueryColumnConfig();
        record.setId(updateQueryColumnConfigVo.getId());
        record.setWidthType(
            EnumUtil.getByCode(GenQueryWidthType.class, updateQueryColumnConfigVo.getWidthType()));
        record.setWidth(updateQueryColumnConfigVo.getWidth());
        record.setSortable(updateQueryColumnConfigVo.getSortable());
        record.setOrderNo(orderNo++);

        getBaseMapper().insert(record);
      }
    }
  }

  @Override
  public GenQueryColumnConfigDto getById(String id) {

    return getBaseMapper().getById(id);
  }

  @Transactional
  @Override
  public void deleteById(String id) {

    getBaseMapper().deleteById(id);
  }
}
