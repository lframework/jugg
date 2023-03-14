package com.lframework.starter.mybatis.service;

import com.lframework.starter.mybatis.entity.DefaultOpLogs;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.vo.CreateOpLogsVo;
import com.lframework.starter.mybatis.vo.QueryOpLogsVo;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 操作日志Service
 *
 * @author zmj
 */
public interface OpLogsService extends BaseMpService<DefaultOpLogs> {

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
  PageResult<DefaultOpLogs> query(Integer pageIndex, Integer pageSize, QueryOpLogsVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  DefaultOpLogs findById(String id);

  /**
   * 根据截止时间删除日志
   *
   * @param endTime
   */
  void clearLogs(LocalDateTime endTime);
}
