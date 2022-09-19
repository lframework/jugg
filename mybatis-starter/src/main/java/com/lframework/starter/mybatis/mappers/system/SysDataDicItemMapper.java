package com.lframework.starter.mybatis.mappers.system;

import com.lframework.starter.mybatis.entity.SysDataDicItem;
import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.starter.mybatis.vo.system.dic.item.QuerySysDataDicItemVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 数据字典值 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface SysDataDicItemMapper extends BaseMapper<SysDataDicItem> {

  /**
   * 查询列表
   *
   * @return
   */
  List<SysDataDicItem> query(@Param("vo") QuerySysDataDicItemVo vo);
}
