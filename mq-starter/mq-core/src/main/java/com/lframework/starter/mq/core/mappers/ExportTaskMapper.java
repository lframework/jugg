package com.lframework.starter.mq.core.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.starter.mq.core.dto.ExportTaskSummaryDto;
import com.lframework.starter.mq.core.entity.ExportTask;

/**
 * <p>
 * ExportTaskMapper 接口
 * </p>
 *
 * @author zmj
 * @since 2025-04-10
 */
public interface ExportTaskMapper extends BaseMapper<ExportTask> {

  /**
   * 根据用户ID统计导出任务
   *
   * @param userId
   * @return
   */
  ExportTaskSummaryDto getSummaryByUserId(String userId);
}
