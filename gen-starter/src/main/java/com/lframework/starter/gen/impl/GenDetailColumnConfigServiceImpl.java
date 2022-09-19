package com.lframework.starter.gen.impl;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.gen.dto.gen.GenDetailColumnConfigDto;
import com.lframework.starter.gen.entity.GenDataEntityDetail;
import com.lframework.starter.gen.entity.GenDetailColumnConfig;
import com.lframework.starter.gen.mappers.GenDetailColumnConfigMapper;
import com.lframework.starter.gen.service.IGenDataEntityDetailService;
import com.lframework.starter.gen.service.IGenDetailColumnConfigService;
import com.lframework.starter.gen.vo.gen.UpdateDetailColumnConfigVo;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenDetailColumnConfigServiceImpl
    extends BaseMpServiceImpl<GenDetailColumnConfigMapper, GenDetailColumnConfig>
    implements IGenDetailColumnConfigService {

  @Autowired
  private IGenDataEntityDetailService genDataEntityDetailService;

  @Override
  public List<GenDetailColumnConfigDto> getByDataEntityId(String entityId) {

    List<GenDataEntityDetail> columns = genDataEntityDetailService.getByEntityId(entityId);
    if (CollectionUtil.isEmpty(columns)) {
      return Collections.EMPTY_LIST;
    }

    return getBaseMapper().getByIds(
        columns.stream().map(GenDataEntityDetail::getId).collect(Collectors.toList()));
  }

  @Transactional
  @Override
  public void updateGenerate(String entityId, List<UpdateDetailColumnConfigVo> vo) {

    List<GenDataEntityDetail> columns = genDataEntityDetailService.getByEntityId(entityId);
    if (!CollectionUtil.isEmpty(columns)) {
      getBaseMapper().deleteBatchIds(
          columns.stream().map(GenDataEntityDetail::getId).collect(Collectors.toList()));
    }

    if (!CollectionUtil.isEmpty(vo)) {
      int orderNo = 1;
      for (UpdateDetailColumnConfigVo updateDetailColumnConfigVo : vo) {
        GenDetailColumnConfig record = new GenDetailColumnConfig();
        record.setId(updateDetailColumnConfigVo.getId());
        record.setSpan(updateDetailColumnConfigVo.getSpan());
        record.setOrderNo(orderNo++);

        getBaseMapper().insert(record);
      }
    }
  }

  @Override
  public GenDetailColumnConfigDto findById(String id) {

    return getBaseMapper().findById(id);
  }

  @Transactional
  @Override
  public void deleteById(String id) {

    getBaseMapper().deleteById(id);
  }
}
