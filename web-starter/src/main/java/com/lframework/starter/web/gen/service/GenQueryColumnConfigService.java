package com.lframework.starter.web.gen.service;

import com.lframework.starter.web.gen.dto.gen.GenQueryColumnConfigDto;
import com.lframework.starter.web.gen.entity.GenQueryColumnConfig;
import com.lframework.starter.web.gen.vo.UpdateQueryColumnConfigVo;
import com.lframework.starter.web.core.service.BaseMpService;
import java.util.List;

public interface GenQueryColumnConfigService extends BaseMpService<GenQueryColumnConfig> {

  /**
   * 根据数据对象ID查询
   *
   * @param entityId
   * @return
   */
  List<GenQueryColumnConfigDto> getByDataEntityId(String entityId);

  /**
   * 修改生成器配置信息
   *
   * @param vo
   */
  void updateGenerate(String entityId, List<UpdateQueryColumnConfigVo> vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenQueryColumnConfigDto findById(String id);

  /**
   * 根据ID查询
   *
   * @param id
   */
  void deleteById(String id);
}
