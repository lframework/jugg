package com.lframework.starter.web.gen.mappers;

import com.lframework.starter.web.gen.vo.simpledb.QuerySimpleTableColumnVo;
import com.lframework.starter.web.gen.dto.simpledb.OriSimpleTableColumnDto;
import com.lframework.starter.web.gen.entity.GenSimpleTableColumn;
import com.lframework.starter.web.core.mapper.BaseMapper;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-05-28
 */
public interface GenSimpleTableColumnMapper extends BaseMapper<GenSimpleTableColumn> {

  /**
   * 根据创建Vo查询
   *
   * @param vo
   * @return
   */
  List<OriSimpleTableColumnDto> query(QuerySimpleTableColumnVo vo);
}
