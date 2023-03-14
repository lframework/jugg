package com.lframework.starter.gen.service;

import com.lframework.starter.gen.entity.GenSimpleTableColumn;
import com.lframework.starter.gen.vo.simpledb.QuerySimpleTableColumnVo;
import com.lframework.starter.mybatis.service.BaseMpService;
import java.util.List;

public interface SimpleTableColumnService extends BaseMpService<GenSimpleTableColumn> {

  /**
   * 查询列信息
   *
   * @param vo
   * @return
   */
  List<GenSimpleTableColumn> query(QuerySimpleTableColumnVo vo);
}
