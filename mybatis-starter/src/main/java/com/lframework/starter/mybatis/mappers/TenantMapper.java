package com.lframework.starter.mybatis.mappers;

import com.lframework.starter.mybatis.entity.Tenant;
import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.starter.mybatis.vo.system.tenant.QueryTenantVo;
import com.lframework.starter.mybatis.vo.system.tenant.TenantSelectorVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TenantMapper extends BaseMapper<Tenant> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<Tenant> query(@Param("vo") QueryTenantVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<Tenant> selector(@Param("vo") TenantSelectorVo vo);
}
