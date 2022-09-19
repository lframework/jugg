package com.lframework.starter.gen.impl;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.gen.dto.gen.GenQueryColumnConfigDto;
import com.lframework.starter.gen.entity.GenDataEntityDetail;
import com.lframework.starter.gen.entity.GenQueryColumnConfig;
import com.lframework.starter.gen.enums.GenQueryWidthType;
import com.lframework.starter.gen.mappers.GenQueryColumnConfigMapper;
import com.lframework.starter.gen.service.IGenDataEntityDetailService;
import com.lframework.starter.gen.service.IGenQueryColumnConfigService;
import com.lframework.starter.gen.vo.gen.UpdateQueryColumnConfigVo;
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
    BaseMpServiceImpl<GenQueryColumnConfigMapper, GenQueryColumnConfig>
    implements IGenQueryColumnConfigService {

  @Autowired
  private IGenDataEntityDetailService genDataEntityDetailService;

  @Override
  public List<GenQueryColumnConfigDto> getByDataEntityId(String entityId) {

    List<GenDataEntityDetail> columns = genDataEntityDetailService.getByEntityId(entityId);
    if (CollectionUtil.isEmpty(columns)) {
      return Collections.EMPTY_LIST;
    }

    return getBaseMapper().getByIds(
        columns.stream().map(GenDataEntityDetail::getId).collect(Collectors.toList()));
  }

  @Transactional
  @Override
  public void updateGenerate(String entityId, List<UpdateQueryColumnConfigVo> vo) {

    List<GenDataEntityDetail> columns = genDataEntityDetailService.getByEntityId(entityId);
    if (!CollectionUtil.isEmpty(columns)) {
      getBaseMapper().deleteBatchIds(
          columns.stream().map(GenDataEntityDetail::getId).collect(Collectors.toList()));
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
  public GenQueryColumnConfigDto findById(String id) {

    return getBaseMapper().findById(id);
  }

  @Transactional
  @Override
  public void deleteById(String id) {

    getBaseMapper().deleteById(id);
  }
}
