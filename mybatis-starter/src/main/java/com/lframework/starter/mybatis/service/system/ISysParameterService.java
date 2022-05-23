package com.lframework.starter.mybatis.service.system;

import com.lframework.common.exceptions.impl.ParameterNotFoundException;
import com.lframework.starter.mybatis.entity.SysParameter;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.starter.mybatis.vo.system.parameter.CreateSysParameterVo;
import com.lframework.starter.mybatis.vo.system.parameter.QuerySysParameterVo;
import com.lframework.starter.mybatis.vo.system.parameter.UpdateSysParameterVo;
import java.util.List;

/**
 * 系统参数 Service
 *
 * @author zmj
 */
public interface ISysParameterService extends BaseMpService<SysParameter> {

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
   * 根据Key查询
   *
   * @param key
   * @return 如果不存在则返回null
   */
  SysParameter findByKey(String key);

  /**
   * 根据Key查询
   *
   * @param key
   * @return 如果不存在则抛异常
   */
  SysParameter findRequiredByKey(String key) throws ParameterNotFoundException;

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
