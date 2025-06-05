package com.lframework.starter.mq.core.service;

import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.starter.mq.core.dto.ExportTaskSummaryDto;
import com.lframework.starter.mq.core.entity.ExportTask;

/**
 * 导出任务 Service
 *
 * @author zmj
 */
public interface ExportTaskService extends BaseMpService<ExportTask> {

  /**
   * 新增
   *
   * @param task
   * @return
   */
  String create(ExportTask task);

  /**
   * 设置为正在导出
   *
   * @param id
   */
  void setExporting(String id);

  /**
   * 设置为成功
   *
   * @param id
   */
  void setSuccess(String id, String securityUploadRecordId, String fileSize);

  /**
   * 设置为失败
   *
   * @param id
   * @param errorMsg
   */
  void setFail(String id, String errorMsg);

  /**
   * 设置数据总条数
   *
   * @param id
   * @param totalCount
   */
  void setTotalCount(String id, Long totalCount);

  /**
   * 增加当前导出条数
   *
   * @param id
   * @param addCount
   */
  void addCurCount(String id, int addCount);

  /**
   * 根据用户ID统计导出任务
   *
   * @param userId
   * @return
   */
  ExportTaskSummaryDto getSummaryByUserId(String userId);
}
