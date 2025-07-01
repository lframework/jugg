package com.lframework.starter.web.gen.service;

import com.lframework.starter.web.gen.dto.gen.GenDetailColumnConfigDto;
import com.lframework.starter.web.gen.entity.GenDetailColumnConfig;
import com.lframework.starter.web.gen.vo.UpdateDetailColumnConfigVo;
import com.lframework.starter.web.core.service.BaseMpService;
import java.util.List;

public interface GenDetailColumnConfigService extends BaseMpService<GenDetailColumnConfig> {

  /**
   * 根据数据实体ID查询
   *
   * @param entityId
   * @return
   */
  List<GenDetailColumnConfigDto> getByDataEntityId(String entityId);

  /**
   * 修改生成器配置信息
   *
   * @param vo
   */
  void updateGenerate(String entityId, List<UpdateDetailColumnConfigVo> vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenDetailColumnConfigDto findById(String id);

  /**
   * 根据ID查询
   *
   * @param id
   */
  void deleteById(String id);
}
