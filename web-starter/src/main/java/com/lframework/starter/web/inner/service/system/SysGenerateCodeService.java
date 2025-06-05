package com.lframework.starter.web.inner.service.system;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.starter.web.inner.entity.SysGenerateCode;
import com.lframework.starter.web.inner.vo.system.generate.CreateSysGenerateCodeVo;
import com.lframework.starter.web.inner.vo.system.generate.QuerySysGenerateCodeVo;
import com.lframework.starter.web.inner.vo.system.generate.SettingSysGenerateCodeVo;
import com.lframework.starter.web.inner.vo.system.generate.UpdateSysGenerateCodeVo;
import java.util.List;

/**
 * 编号规则 Service
 *
 * @author zmj
 */
public interface SysGenerateCodeService extends BaseMpService<SysGenerateCode> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<SysGenerateCode> query(Integer pageIndex, Integer pageSize, QuerySysGenerateCodeVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<SysGenerateCode> query(QuerySysGenerateCodeVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SysGenerateCode findById(Integer id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  Integer create(CreateSysGenerateCodeVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSysGenerateCodeVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   * @return
   */
  void deleteById(Integer id);

  /**
   * 设置
   *
   * @param vo
   */
  void setting(SettingSysGenerateCodeVo vo);
}
