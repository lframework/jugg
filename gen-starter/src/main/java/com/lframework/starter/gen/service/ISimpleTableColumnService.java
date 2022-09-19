package com.lframework.starter.gen.service;

import com.lframework.starter.gen.entity.GenSimpleTableColumn;
import com.lframework.starter.gen.vo.simpledb.QuerySimpleTableColumnVo;
import com.lframework.starter.mybatis.service.BaseMpService;
import java.util.List;

public interface ISimpleTableColumnService extends BaseMpService<GenSimpleTableColumn> {

  /**
   * 根据TableId查询
   *
   * @param id
   * @return
   */
  List<GenSimpleTableColumn> getByTableId(String id);

  /**
   * 根据数据表id删除
   *
   * @param tableId
   */
  void deleteByTableId(String tableId);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenSimpleTableColumn findById(String id);

  /**
   * 查询列信息
   *
   * @param vo
   * @return
   */
  List<GenSimpleTableColumn> query(QuerySimpleTableColumnVo vo);
}
