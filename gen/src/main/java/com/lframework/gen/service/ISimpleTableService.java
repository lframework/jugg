package com.lframework.gen.service;

import com.lframework.gen.dto.simpledb.SimpleTableDto;
import com.lframework.gen.entity.GenSimpleTable;
import com.lframework.gen.vo.simpledb.CreateSimpleTableVo;
import com.lframework.starter.mybatis.service.BaseMpService;

public interface ISimpleTableService extends BaseMpService<GenSimpleTable> {

  /**
   * 根据数据对象ID查询
   *
   * @param id
   * @return
   */
  SimpleTableDto getByDataObjId(String id);

  /**
   * 创建数据表对象
   *
   * @param vo
   */
  String create(CreateSimpleTableVo vo);

  /**
   * 根据DataObjId删除
   *
   * @param dataObjId
   */
  void deleteByDataObjId(String dataObjId);
}
