package com.lframework.starter.web.inner.mappers.system;

import com.lframework.starter.web.inner.entity.SysDept;
import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.starter.web.inner.vo.system.dept.SysDeptSelectorVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门 Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-06-26
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {

  /**
   * 选择器
   *
   * @return
   */
  List<SysDept> selector(@Param("vo") SysDeptSelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SysDept findById(String id);
}
