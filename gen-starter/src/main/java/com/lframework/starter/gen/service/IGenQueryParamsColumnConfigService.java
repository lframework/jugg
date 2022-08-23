package com.lframework.starter.gen.service;

import com.lframework.starter.gen.dto.dataobj.GenQueryParamsColumnConfigDto;
import com.lframework.starter.gen.entity.GenQueryParamsColumnConfig;
import com.lframework.starter.gen.vo.dataobj.UpdateQueryParamsColumnConfigVo;
import com.lframework.starter.mybatis.service.BaseMpService;
import java.util.List;

public interface IGenQueryParamsColumnConfigService extends
    BaseMpService<GenQueryParamsColumnConfig> {

  /**
   * 根据数据对象ID查询
   *
   * @param dataObjId
   * @return
   */
  List<GenQueryParamsColumnConfigDto> getByDataObjId(String dataObjId);

  /**
   * 修改生成器配置信息
   *
   * @param vo
   */
  void updateGenerate(String dataObjId, List<UpdateQueryParamsColumnConfigVo> vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenQueryParamsColumnConfigDto findById(String id);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);
}
