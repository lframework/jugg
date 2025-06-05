package com.lframework.starter.web.inner.service;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.starter.web.inner.entity.OpLogs;
import com.lframework.starter.web.inner.vo.oplogs.CreateOpLogsVo;
import com.lframework.starter.web.inner.vo.oplogs.QueryOpLogsVo;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 操作日志Service
 *
 * @author zmj
 */
public interface OpLogsService extends BaseMpService<OpLogs> {

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateOpLogsVo vo);

  /**
   * 创建
   *
   * @param list
   * @return
   */
  void create(Collection<CreateOpLogsVo> list);

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<OpLogs> query(Integer pageIndex, Integer pageSize, QueryOpLogsVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  OpLogs findById(String id);

  /**
   * 根据截止时间删除日志
   *
   * @param endTime
   */
  void clearLogs(LocalDateTime endTime);
}
