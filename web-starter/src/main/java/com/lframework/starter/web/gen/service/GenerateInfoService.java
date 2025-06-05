package com.lframework.starter.web.gen.service;

import com.lframework.starter.web.gen.dto.gen.GenGenerateInfoDto;
import com.lframework.starter.web.gen.entity.GenGenerateInfo;
import com.lframework.starter.web.gen.vo.UpdateGenerateInfoVo;
import com.lframework.starter.web.core.service.BaseMpService;

public interface GenerateInfoService extends BaseMpService<GenGenerateInfo> {

  /**
   * 根据数据实体ID查询
   *
   * @param entityId
   * @return
   */
  GenGenerateInfoDto getByEntityId(String entityId);

  /**
   * 修改生成器配置信息
   *
   * @param vo
   */
  void updateGenerate(String dataObjId, UpdateGenerateInfoVo vo);

  /**
   * 根据数据实体ID删除
   *
   * @param entityId
   */
  void deleteByEntityId(String entityId);
}
