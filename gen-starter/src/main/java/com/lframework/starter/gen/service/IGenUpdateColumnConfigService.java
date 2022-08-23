package com.lframework.starter.gen.service;

import com.lframework.starter.gen.dto.dataobj.GenUpdateColumnConfigDto;
import com.lframework.starter.gen.entity.GenUpdateColumnConfig;
import com.lframework.starter.gen.vo.dataobj.UpdateUpdateColumnConfigVo;
import com.lframework.starter.mybatis.service.BaseMpService;
import java.util.List;

public interface IGenUpdateColumnConfigService extends BaseMpService<GenUpdateColumnConfig> {

  /**
   * 根据数据对象ID查询
   *
   * @param dataObjId
   * @return
   */
  List<GenUpdateColumnConfigDto> getByDataObjId(String dataObjId);

  /**
   * 修改生成器配置信息
   *
   * @param vo
   */
  void updateGenerate(String dataObjId, List<UpdateUpdateColumnConfigVo> vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenUpdateColumnConfigDto findById(String id);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);
}
