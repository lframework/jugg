package com.lframework.starter.gen.service;

import com.lframework.starter.gen.dto.simpledb.SimpleTableDto;
import com.lframework.starter.gen.entity.GenSimpleTable;
import com.lframework.starter.mybatis.service.BaseMpService;

public interface ISimpleTableService extends BaseMpService<GenSimpleTable> {

  /**
   * 根据数据实体ID查询
   *
   * @param id
   * @return
   */
  SimpleTableDto getByEntityId(String id);

  /**
   * 根据实体ID删除
   *
   * @param entityId
   */
  void deleteByEntityId(String entityId);
}
