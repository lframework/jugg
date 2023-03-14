package com.lframework.starter.gen.service;

import com.lframework.starter.gen.entity.GenDataEntityDetail;
import com.lframework.starter.gen.vo.data.entity.GenDataEntityDetailSelectorVo;
import com.lframework.starter.mybatis.service.BaseMpService;
import java.util.List;

public interface GenDataEntityDetailService extends BaseMpService<GenDataEntityDetail> {

  /**
   * 根据实体ID查询
   *
   * @param entityId
   * @return
   */
  List<GenDataEntityDetail> getByEntityId(String entityId);

  /**
   * 根据实体ID删除
   *
   * @param entityId
   */
  void deleteByEntityId(String entityId);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<GenDataEntityDetail> selector(GenDataEntityDetailSelectorVo vo);
}
