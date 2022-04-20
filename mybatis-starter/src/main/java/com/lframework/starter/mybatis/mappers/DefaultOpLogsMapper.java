package com.lframework.starter.mybatis.mappers;

import com.lframework.starter.mybatis.dto.DefaultOpLogsDto;
import com.lframework.starter.mybatis.entity.DefaultOpLogs;
import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.starter.mybatis.vo.QueryOpLogsVo;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 操作日志 Mapper
 * </p>
 *
 * @author zmj
 */
public interface DefaultOpLogsMapper extends BaseMapper<DefaultOpLogs> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<DefaultOpLogsDto> query(@Param("vo") QueryOpLogsVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  DefaultOpLogsDto findById(String id);

  /**
   * 根据截止时间删除日志
   *
   * @param endTime
   */
  void clearLogs(@Param("endTime") LocalDateTime endTime);
}
