package com.lframework.starter.mybatis.mappers.system;

import com.lframework.starter.mybatis.entity.SysDataDic;
import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.starter.mybatis.vo.system.dic.QuerySysDataDicVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface SysDataDicMapper extends BaseMapper<SysDataDic> {

  /**
   * 查询列表
   *
   * @return
   */
  List<SysDataDic> query(@Param("vo") QuerySysDataDicVo vo);
}
