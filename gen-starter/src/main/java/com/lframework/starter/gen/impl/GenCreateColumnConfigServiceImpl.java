package com.lframework.starter.gen.impl;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.gen.dto.gen.GenCreateColumnConfigDto;
import com.lframework.starter.gen.entity.GenCreateColumnConfig;
import com.lframework.starter.gen.entity.GenDataEntityDetail;
import com.lframework.starter.gen.mappers.GenCreateColumnConfigMapper;
import com.lframework.starter.gen.service.IGenCreateColumnConfigService;
import com.lframework.starter.gen.service.IGenDataEntityDetailService;
import com.lframework.starter.gen.vo.gen.UpdateCreateColumnConfigVo;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenCreateColumnConfigServiceImpl
    extends BaseMpServiceImpl<GenCreateColumnConfigMapper, GenCreateColumnConfig>
    implements IGenCreateColumnConfigService {


  @Autowired
  private IGenDataEntityDetailService genDataEntityDetailService;

  @Override
  public List<GenCreateColumnConfigDto> getByDataEntityId(String entityId) {

    List<GenDataEntityDetail> columns = genDataEntityDetailService.getByEntityId(entityId);
    if (CollectionUtil.isEmpty(columns)) {
      return Collections.EMPTY_LIST;
    }

    return getBaseMapper().getByIds(
        columns.stream().map(GenDataEntityDetail::getId).collect(Collectors.toList()));
  }

  @Transactional
  @Override
  public void updateGenerate(String entityId, List<UpdateCreateColumnConfigVo> vo) {

    List<GenDataEntityDetail> columns = genDataEntityDetailService.getByEntityId(entityId);
    if (!CollectionUtil.isEmpty(columns)) {
      getBaseMapper().deleteBatchIds(
          columns.stream().map(GenDataEntityDetail::getId).collect(Collectors.toList()));
    }

    if (!CollectionUtil.isEmpty(vo)) {
      int orderNo = 1;
      for (UpdateCreateColumnConfigVo updateCreateColumnConfigVo : vo) {
        GenCreateColumnConfig record = new GenCreateColumnConfig();
        record.setId(updateCreateColumnConfigVo.getId());
        record.setRequired(updateCreateColumnConfigVo.getRequired());
        record.setOrderNo(orderNo++);

        getBaseMapper().insert(record);
      }
    }
  }

  @Override
  public GenCreateColumnConfigDto findById(String id) {

    return getBaseMapper().findById(id);
  }

  @Transactional
  @Override
  public void deleteById(String id) {

    getBaseMapper().deleteById(id);
  }
}
