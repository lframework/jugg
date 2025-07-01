package com.lframework.starter.web.inner.service;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.starter.web.inner.entity.Tenant;
import com.lframework.starter.web.inner.vo.system.tenant.CreateTenantVo;
import com.lframework.starter.web.inner.vo.system.tenant.QueryTenantVo;
import com.lframework.starter.web.inner.vo.system.tenant.TenantSelectorVo;
import com.lframework.starter.web.inner.vo.system.tenant.UpdateTenantVo;

/**
 * 租户Service
 *
 * @author zmj
 */
public interface TenantService extends BaseMpService<Tenant> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<Tenant> query(Integer pageIndex, Integer pageSize, QueryTenantVo vo);

  /**
   * 选择器
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<Tenant> selector(Integer pageIndex, Integer pageSize, TenantSelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  Tenant findById(Integer id);

  /**
   * 新增
   *
   * @param data
   * @return
   */
  Integer create(CreateTenantVo data);

  /**
   * 修改
   *
   * @param data
   */
  void update(UpdateTenantVo data);
}
