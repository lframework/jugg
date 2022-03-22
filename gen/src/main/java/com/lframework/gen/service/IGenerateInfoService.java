package com.lframework.gen.service;

import com.lframework.gen.dto.dataobj.GenGenerateInfoDto;
import com.lframework.gen.vo.dataobj.UpdateGenerateInfoVo;
import com.lframework.starter.web.service.BaseService;

public interface IGenerateInfoService extends BaseService {

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
