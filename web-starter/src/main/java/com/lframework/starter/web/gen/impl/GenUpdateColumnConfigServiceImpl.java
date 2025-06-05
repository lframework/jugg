package com.lframework.starter.web.gen.impl;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.gen.dto.gen.GenUpdateColumnConfigDto;
import com.lframework.starter.web.gen.entity.GenDataEntityDetail;
import com.lframework.starter.web.gen.entity.GenUpdateColumnConfig;
import com.lframework.starter.web.gen.mappers.GenUpdateColumnConfigMapper;
import com.lframework.starter.web.gen.service.GenDataEntityDetailService;
import com.lframework.starter.web.gen.service.GenUpdateColumnConfigService;
import com.lframework.starter.web.gen.vo.UpdateUpdateColumnConfigVo;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenUpdateColumnConfigServiceImpl
    extends BaseMpServiceImpl<GenUpdateColumnConfigMapper, GenUpdateColumnConfig>
    implements GenUpdateColumnConfigService {

  @Autowired
  private GenDataEntityDetailService genDataEntityDetailService;

  @Override
  public List<GenUpdateColumnConfigDto> getByDataEntityId(String entityId) {

    List<GenDataEntityDetail> columns = genDataEntityDetailService.getByEntityId(entityId);
    if (CollectionUtil.isEmpty(columns)) {
      return CollectionUtil.emptyList();
    }

    return getBaseMapper().getByIds(
        columns.stream().map(GenDataEntityDetail::getId).collect(Collectors.toList()));
  }

  @Transactional(rollbackFor = Exception.class)
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

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    getBaseMapper().deleteById(id);
  }
}
