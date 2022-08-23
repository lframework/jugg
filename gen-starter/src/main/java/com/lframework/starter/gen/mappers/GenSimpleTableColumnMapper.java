package com.lframework.starter.gen.mappers;

import com.lframework.starter.gen.dto.simpledb.OriSimpleTableColumnDto;
import com.lframework.starter.gen.dto.simpledb.SimpleTableColumnDto;
import com.lframework.starter.gen.entity.GenSimpleTableColumn;
import com.lframework.starter.gen.vo.simpledb.CreateSimpleTableVo;
import com.lframework.starter.mybatis.mapper.BaseMapper;
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
   * 根据TableId查询
   *
   * @param id
   * @return
   */
  List<SimpleTableColumnDto> getByTableId(String id);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SimpleTableColumnDto findById(String id);

  /**
   * 根据创建Vo查询
   *
   * @param vo
   * @return
   */
  List<OriSimpleTableColumnDto> get(CreateSimpleTableVo vo);
}
