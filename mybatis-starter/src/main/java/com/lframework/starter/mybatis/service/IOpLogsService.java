package com.lframework.starter.mybatis.service;

import com.lframework.starter.mybatis.dto.DefaultOpLogsDto;
import com.lframework.starter.mybatis.entity.DefaultOpLogs;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.vo.CreateOpLogsVo;
import com.lframework.starter.mybatis.vo.QueryOpLogsVo;
import java.time.LocalDateTime;

/**
 * 操作日志Service
 *
 * @author zmj
 */
public interface IOpLogsService extends BaseMpService<DefaultOpLogs> {

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateOpLogsVo vo);

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<DefaultOpLogsDto> query(Integer pageIndex, Integer pageSize, QueryOpLogsVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  DefaultOpLogsDto getById(String id);

  /**
   * 根据截止时间删除日志
   *
   * @param endTime
   */
  void clearLogs(LocalDateTime endTime);
}
