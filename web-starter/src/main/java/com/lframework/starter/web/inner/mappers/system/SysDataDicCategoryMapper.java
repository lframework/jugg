package com.lframework.starter.web.inner.mappers.system;

import com.lframework.starter.web.inner.entity.SysDataDicCategory;
import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.starter.web.inner.vo.system.dic.category.SysDataDicCategorySelectorVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 数据字典分类 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface SysDataDicCategoryMapper extends BaseMapper<SysDataDicCategory> {

  /**
   * 查询列表
   *
   * @return
   */
  List<SysDataDicCategory> query();

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<SysDataDicCategory> selector(@Param("vo") SysDataDicCategorySelectorVo vo);
}
