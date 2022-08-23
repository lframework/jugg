package com.lframework.starter.gen.service;

import com.lframework.starter.gen.dto.dataobj.GenQueryColumnConfigDto;
import com.lframework.starter.gen.entity.GenQueryColumnConfig;
import com.lframework.starter.gen.vo.dataobj.UpdateQueryColumnConfigVo;
import com.lframework.starter.mybatis.service.BaseMpService;
import java.util.List;

public interface IGenQueryColumnConfigService extends BaseMpService<GenQueryColumnConfig> {

  /**
   * 根据数据对象ID查询
   *
   * @param dataObjId
   * @return
   */
  List<GenQueryColumnConfigDto> getByDataObjId(String dataObjId);

  /**
   * 修改生成器配置信息
   *
   * @param vo
   */
  void updateGenerate(String dataObjId, List<UpdateQueryColumnConfigVo> vo);

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
