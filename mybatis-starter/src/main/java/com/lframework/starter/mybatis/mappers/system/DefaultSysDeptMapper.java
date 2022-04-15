package com.lframework.starter.mybatis.mappers.system;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.starter.mybatis.dto.system.dept.DefaultSysDeptDto;
import com.lframework.starter.mybatis.entity.DefaultSysDept;
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
  List<DefaultSysDeptDto> selector();

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  DefaultSysDeptDto getById(String id);
}
