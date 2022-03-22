package com.lframework.gen.service;

import com.lframework.gen.dto.dataobj.GenDetailColumnConfigDto;
import com.lframework.gen.vo.dataobj.UpdateDetailColumnConfigVo;
import com.lframework.starter.web.service.BaseService;
import java.util.List;

public interface IGenDetailColumnConfigService extends BaseService {

  /**
   * 根据数据对象ID查询
   *
   * @param dataObjId
   * @return
   */
  List<GenDetailColumnConfigDto> getByDataObjId(String dataObjId);

  /**
   * 修改生成器配置信息
   *
   * @param vo
   */
  void updateGenerate(String dataObjId, List<UpdateDetailColumnConfigVo> vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenDetailColumnConfigDto getById(String id);

  /**
   * 根据ID查询
   *
   * @param id
   */
  void deleteById(String id);
}
