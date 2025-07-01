package com.lframework.starter.web.gen.mappers;

import com.lframework.starter.web.gen.entity.GenCustomPage;
import com.lframework.starter.web.gen.vo.custom.page.GenCustomPageSelectorVo;
import com.lframework.starter.web.gen.vo.custom.page.QueryGenCustomPageVo;
import com.lframework.starter.web.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 自定义页面 Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2023-06-20
 */
public interface GenCustomPageMapper extends BaseMapper<GenCustomPage> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<GenCustomPage> query(@Param("vo") QueryGenCustomPageVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<GenCustomPage> selector(@Param("vo") GenCustomPageSelectorVo vo);
}
