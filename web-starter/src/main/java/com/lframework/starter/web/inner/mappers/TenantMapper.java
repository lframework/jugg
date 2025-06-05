package com.lframework.starter.web.inner.mappers;

import com.lframework.starter.web.core.annotations.sort.Sort;
import com.lframework.starter.web.core.annotations.sort.Sorts;
import com.lframework.starter.web.inner.entity.Tenant;
import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.starter.web.inner.vo.system.tenant.QueryTenantVo;
import com.lframework.starter.web.inner.vo.system.tenant.TenantSelectorVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TenantMapper extends BaseMapper<Tenant> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @Sorts({
      @Sort(value = "id", alias = "tb", autoParse = true),
      @Sort(value = "name", alias = "tb", autoParse = true),
  })
  List<Tenant> query(@Param("vo") QueryTenantVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<Tenant> selector(@Param("vo") TenantSelectorVo vo);
}
