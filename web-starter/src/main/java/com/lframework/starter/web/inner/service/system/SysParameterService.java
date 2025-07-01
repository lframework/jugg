package com.lframework.starter.web.inner.service.system;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.starter.web.inner.entity.SysParameter;
import com.lframework.starter.web.inner.vo.system.parameter.CreateSysParameterVo;
import com.lframework.starter.web.inner.vo.system.parameter.QuerySysParameterVo;
import com.lframework.starter.web.inner.vo.system.parameter.UpdateSysParameterVo;
import java.util.List;

/**
 * 系统参数 Service
 *
 * @author zmj
 */
public interface SysParameterService extends BaseMpService<SysParameter> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<SysParameter> query(Integer pageIndex, Integer pageSize, QuerySysParameterVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<SysParameter> query(QuerySysParameterVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SysParameter findById(Long id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  Long create(CreateSysParameterVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSysParameterVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   * @return
   */
  void deleteById(Long id);
}
