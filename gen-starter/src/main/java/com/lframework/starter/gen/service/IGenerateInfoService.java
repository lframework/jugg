package com.lframework.starter.gen.service;

import com.lframework.starter.gen.dto.gen.GenGenerateInfoDto;
import com.lframework.starter.gen.entity.GenGenerateInfo;
import com.lframework.starter.gen.vo.gen.UpdateGenerateInfoVo;
import com.lframework.starter.mybatis.service.BaseMpService;

public interface IGenerateInfoService extends BaseMpService<GenGenerateInfo> {

  /**
   * 根据数据对象ID查询
   *
   * @param dataObjId
   * @return
   */
  GenGenerateInfoDto getByDataObjId(String dataObjId);

  /**
   * 修改生成器配置信息
   *
   * @param vo
   */
  void updateGenerate(String dataObjId, UpdateGenerateInfoVo vo);

  /**
   * 根据数据对象ID删除
   *
   * @param dataObjId
   */
  void deleteByDataObjId(String dataObjId);
}
