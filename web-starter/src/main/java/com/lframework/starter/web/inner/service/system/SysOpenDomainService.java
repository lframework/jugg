package com.lframework.starter.web.inner.service.system;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.starter.web.inner.entity.SysOpenDomain;
import com.lframework.starter.web.inner.vo.system.open.QuerySysOpenDomainVo;
import com.lframework.starter.web.inner.vo.system.open.CreateSysOpenDomainVo;
import com.lframework.starter.web.inner.vo.system.open.SysOpenDomainSelectorVo;
import com.lframework.starter.web.inner.vo.system.open.UpdateSysOpenDomainSecretVo;
import com.lframework.starter.web.inner.vo.system.open.UpdateSysOpenDomainVo;

public interface SysOpenDomainService extends BaseMpService<SysOpenDomain> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<SysOpenDomain> query(QuerySysOpenDomainVo vo);

  /**
   * 选择器
   *
   * @return
   */
  PageResult<SysOpenDomain> selector(SysOpenDomainSelectorVo vo);


  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SysOpenDomain findById(Integer id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateSysOpenDomainVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSysOpenDomainVo vo);

  /**
   * 修改Api密钥
   *
   * @param vo
   */
  void updateApiSecret(UpdateSysOpenDomainSecretVo vo);
}
