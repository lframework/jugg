package com.lframework.starter.web.inner.service;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseService;
import com.lframework.starter.web.inner.vo.qrtz.CreateQrtzVo;
import com.lframework.starter.web.inner.vo.qrtz.UpdateQrtzVo;
import com.lframework.starter.web.inner.vo.qrtz.QueryQrtzVo;
import com.lframework.starter.web.inner.dto.qrtz.QrtzDto;

/**
 * @author zmj
 * @since 2022/8/20
 */
public interface QrtzService extends BaseService {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<QrtzDto> query(Integer pageIndex, Integer pageSize, QueryQrtzVo vo);

  /**
   * 根据ID查询
   *
   * @param name
   * @param group
   * @return
   */
  QrtzDto findById(String name, String group);

  /**
   * 创建
   *
   * @param vo
   */
  void create(CreateQrtzVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateQrtzVo vo);

  /**
   * 恢复
   *
   * @param name
   * @param group
   */
  void resume(String name, String group);

  /**
   * 暂停
   *
   * @param name
   * @param group
   */
  void pause(String name, String group);

  /**
   * 触发
   *
   * @param name
   * @param group
   */
  void trigger(String name, String group);

  /**
   * 删除
   *
   * @param name
   * @param group
   */
  void delete(String name, String group);
}
