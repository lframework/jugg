package com.lframework.starter.web.gen.service;

import com.lframework.starter.web.gen.vo.simpledb.QuerySimpleTableColumnVo;
import com.lframework.starter.web.gen.entity.GenSimpleTableColumn;
import com.lframework.starter.web.core.service.BaseMpService;
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
