package com.lframework.starter.mybatis.service.system;

import com.lframework.starter.mybatis.dto.system.position.DefaultSysPositionDto;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.vo.system.position.UpdateSysPositionVo;
import com.lframework.starter.mybatis.vo.system.position.CreateSysPositionVo;
import com.lframework.starter.mybatis.vo.system.position.QuerySysPositionVo;
import com.lframework.starter.mybatis.vo.system.position.SysPositionSelectorVo;
import com.lframework.starter.web.service.BaseService;
import java.util.Collection;

public interface ISysPositionService extends BaseService {

  /**
   * 查询全部岗位信息
   *
   * @return
   */
  PageResult<DefaultSysPositionDto> query(Integer pageIndex, Integer pageSize,
      QuerySysPositionVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  DefaultSysPositionDto getById(String id);

  /**
   * 选择器
   *
   * @return
   */
  PageResult<DefaultSysPositionDto> selector(Integer pageIndex, Integer pageSize,
      SysPositionSelectorVo vo);

  /**
   * 根据ID停用
   *
   * @param ids
   */
  void batchUnable(Collection<String> ids);

  /**
   * 根据ID启用
   *
   * @param ids
   */
  void batchEnable(Collection<String> ids);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateSysPositionVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSysPositionVo vo);
}
