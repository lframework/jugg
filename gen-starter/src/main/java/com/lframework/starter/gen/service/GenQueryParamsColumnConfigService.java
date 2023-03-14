package com.lframework.starter.gen.service;

import com.lframework.starter.gen.dto.gen.GenQueryParamsColumnConfigDto;
import com.lframework.starter.gen.entity.GenQueryParamsColumnConfig;
import com.lframework.starter.gen.vo.gen.UpdateQueryParamsColumnConfigVo;
import com.lframework.starter.mybatis.service.BaseMpService;
import java.util.List;

public interface GenQueryParamsColumnConfigService extends
    BaseMpService<GenQueryParamsColumnConfig> {

  /**
   * 根据数据实体ID查询
   *
   * @param entityId
   * @return
   */
  List<GenQueryParamsColumnConfigDto> getByDataEntityId(String entityId);

  /**
   * 修改生成器配置信息
   *
   * @param vo
   */
  void updateGenerate(String entityId, List<UpdateQueryParamsColumnConfigVo> vo);

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
