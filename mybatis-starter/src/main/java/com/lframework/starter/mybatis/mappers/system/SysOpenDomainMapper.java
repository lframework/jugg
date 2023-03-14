package com.lframework.starter.mybatis.mappers.system;

import com.lframework.starter.mybatis.entity.SysOpenDomain;
import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.starter.mybatis.vo.system.open.QuerySysOpenDomainVo;
import com.lframework.starter.mybatis.vo.system.open.SysOpenDomainSelectorVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-02
 */
public interface SysOpenDomainMapper extends BaseMapper<SysOpenDomain> {

  /**
   * 查询列表
   *
   * @return
   */
  List<SysOpenDomain> query(@Param("vo") QuerySysOpenDomainVo vo);

  /**
   * 选择器
   *
   * @return
   */
  List<SysOpenDomain> selector(@Param("vo") SysOpenDomainSelectorVo vo);
}
