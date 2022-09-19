package com.lframework.starter.gen.impl;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.gen.dto.gen.GenUpdateColumnConfigDto;
import com.lframework.starter.gen.entity.GenDataEntityDetail;
import com.lframework.starter.gen.entity.GenUpdateColumnConfig;
import com.lframework.starter.gen.mappers.GenUpdateColumnConfigMapper;
import com.lframework.starter.gen.service.IGenDataEntityDetailService;
import com.lframework.starter.gen.service.IGenUpdateColumnConfigService;
import com.lframework.starter.gen.vo.gen.UpdateUpdateColumnConfigVo;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenUpdateColumnConfigServiceImpl
    extends BaseMpServiceImpl<GenUpdateColumnConfigMapper, GenUpdateColumnConfig>
    implements IGenUpdateColumnConfigService {

  @Autowired
  private IGenDataEntityDetailService genDataEntityDetailService;

  @Override
  public List<GenUpdateColumnConfigDto> getByDataEntityId(String entityId) {

    List<GenDataEntityDetail> columns = genDataEntityDetailService.getByEntityId(entityId);
    if (CollectionUtil.isEmpty(columns)) {
      return Collections.EMPTY_LIST;
    }

    return getBaseMapper().getByIds(
        columns.stream().map(GenDataEntityDetail::getId).collect(Collectors.toList()));
  }

  @Transactional
  @Override
  public void updateGenerate(String entityId, List<UpdateUpdateColumnConfigVo> vo) {

    List<GenDataEntityDetail> columns = genDataEntityDetailService.getByEntityId(entityId);
    if (!CollectionUtil.isEmpty(columns)) {
      getBaseMapper().deleteBatchIds(
          columns.stream().map(GenDataEntityDetail::getId).collect(Collectors.toList()));
    }

    if (!CollectionUtil.isEmpty(vo)) {
      int orderNo = 1;
      for (UpdateUpdateColumnConfigVo updateUpdateColumnConfigVo : vo) {
        GenUpdateColumnConfig record = new GenUpdateColumnConfig();
        record.setId(updateUpdateColumnConfigVo.getId());
        record.setRequired(updateUpdateColumnConfigVo.getRequired());
        record.setOrderNo(orderNo++);

        getBaseMapper().insert(record);
      }
    }
  }

  @Override
  public GenUpdateColumnConfigDto findById(String id) {

    return getBaseMapper().findById(id);
  }

  @Transactional
  @Override
  public void deleteById(String id) {

    getBaseMapper().deleteById(id);
  }
}
