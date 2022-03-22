package com.lframework.gen.service;

import com.lframework.gen.dto.simpledb.SimpleTableColumnDto;
import com.lframework.gen.vo.simpledb.CreateSimpleTableVo;
import com.lframework.starter.web.service.BaseService;
import java.util.List;

public interface ISimpleTableColumnService extends BaseService {

  /**
   * 根据TableId查询
   *
   * @param id
   * @return
   */
  List<SimpleTableColumnDto> getByTableId(String id);

  /**
   * 根据数据表id删除
   *
   * @param tableId
   */
  void deleteByTableId(String tableId);

  /**
   * 创建
   *
   * @param tableId
   * @param vo
   * @return
   */
  void create(String tableId, CreateSimpleTableVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SimpleTableColumnDto getById(String id);
}
