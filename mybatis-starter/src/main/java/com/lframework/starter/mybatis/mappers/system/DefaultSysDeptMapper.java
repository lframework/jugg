package com.lframework.starter.mybatis.mappers.system;

import com.lframework.starter.mybatis.entity.DefaultSysDept;
import com.lframework.starter.mybatis.mapper.BaseMapper;
import java.util.List;

/**
 * <p>
 * 部门 Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-06-26
 */
public interface DefaultSysDeptMapper extends BaseMapper<DefaultSysDept> {

  /**
   * 选择器
   *
   * @return
   */
  List<DefaultSysDept> selector();

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  DefaultSysDept findById(String id);
}
