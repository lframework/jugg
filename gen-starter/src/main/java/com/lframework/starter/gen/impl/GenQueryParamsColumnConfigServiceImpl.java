package com.lframework.starter.gen.impl;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.gen.dto.gen.GenQueryParamsColumnConfigDto;
import com.lframework.starter.gen.entity.GenDataEntityDetail;
import com.lframework.starter.gen.entity.GenQueryParamsColumnConfig;
import com.lframework.starter.gen.enums.GenQueryType;
import com.lframework.starter.gen.mappers.GenQueryParamsColumnConfigMapper;
import com.lframework.starter.gen.service.IGenDataEntityDetailService;
import com.lframework.starter.gen.service.IGenQueryParamsColumnConfigService;
import com.lframework.starter.gen.vo.gen.UpdateQueryParamsColumnConfigVo;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.EnumUtil;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenQueryParamsColumnConfigServiceImpl
    extends BaseMpServiceImpl<GenQueryParamsColumnConfigMapper, GenQueryParamsColumnConfig>
    implements IGenQueryParamsColumnConfigService {

  @Autowired
  private IGenDataEntityDetailService genDataEntityDetailService;

  @Override
  public List<GenQueryParamsColumnConfigDto> getByDataEntityId(String entityId) {

    List<GenDataEntityDetail> columns = genDataEntityDetailService.getByEntityId(entityId);
    if (CollectionUtil.isEmpty(columns)) {
      return Collections.EMPTY_LIST;
    }

    return getBaseMapper().getByIds(
        columns.stream().map(GenDataEntityDetail::getId).collect(Collectors.toList()));
  }

  @Transactional
  @Override
  public void updateGenerate(String entityId, List<UpdateQueryParamsColumnConfigVo> vo) {

    List<GenDataEntityDetail> columns = genDataEntityDetailService.getByEntityId(entityId);
    if (!CollectionUtil.isEmpty(columns)) {
      getBaseMapper().deleteBatchIds(
          columns.stream().map(GenDataEntityDetail::getId).collect(Collectors.toList()));
    }

    if (!CollectionUtil.isEmpty(vo)) {
      int orderNo = 1;
      for (UpdateQueryParamsColumnConfigVo updateQueryParamsColumnConfigVo : vo) {
        GenQueryParamsColumnConfig record = new GenQueryParamsColumnConfig();
        record.setId(updateQueryParamsColumnConfigVo.getId());
        record.setQueryType(
            EnumUtil.getByCode(GenQueryType.class, updateQueryParamsColumnConfigVo.getQueryType()));
        record.setOrderNo(orderNo++);

        getBaseMapper().insert(record);
      }
    }
  }

  @Override
  public GenQueryParamsColumnConfigDto findById(String id) {

    return getBaseMapper().findById(id);
  }

  @Transactional
  @Override
  public void deleteById(String id) {

    getBaseMapper().deleteById(id);
  }
}
