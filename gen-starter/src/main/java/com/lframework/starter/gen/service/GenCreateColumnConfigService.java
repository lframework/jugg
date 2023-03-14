package com.lframework.starter.gen.service;

import com.lframework.starter.gen.dto.gen.GenCreateColumnConfigDto;
import com.lframework.starter.gen.entity.GenCreateColumnConfig;
import com.lframework.starter.gen.vo.gen.UpdateCreateColumnConfigVo;
import com.lframework.starter.mybatis.service.BaseMpService;
import java.util.List;

public interface GenCreateColumnConfigService extends BaseMpService<GenCreateColumnConfig> {

  /**
   * 根据数据实体ID查询
   *
   * @param entityId
   * @return
   */
  List<GenCreateColumnConfigDto> getByDataEntityId(String entityId);

  /**
   * 修改生成器配置信息
   *
   * @param vo
   */
  void updateGenerate(String entityId, List<UpdateCreateColumnConfigVo> vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenCreateColumnConfigDto findById(String id);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);
}
