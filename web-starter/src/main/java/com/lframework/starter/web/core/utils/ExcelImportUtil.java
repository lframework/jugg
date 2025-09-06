package com.lframework.starter.web.core.utils;

import cn.dev33.satoken.stp.StpUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.ExcelImportBo;

/**
 * Excel导入工具类
 * 提供Excel导入任务管理功能，包括任务创建、进度跟踪、状态更新等
 * 支持异步导入任务的进度监控和错误处理
 *
 * @author lframework@163.com
 */
public class ExcelImportUtil {

  private static final String UPLOAD_TASK_KEY = "upload_task_{}";

  /**
   * 获取Excel导入任务
   * 从Session中获取指定ID的导入任务，如果不存在则创建新任务
   *
   * @param taskId 任务ID，不能为null
   * @return Excel导入任务对象
   */
  public static ExcelImportBo getTask(String taskId) {
    ExcelImportBo task = (ExcelImportBo) StpUtil.getSession()
        .get(StringUtil.format(UPLOAD_TASK_KEY, taskId));
    if (task == null) {
      task = new ExcelImportBo();
      task.setId(taskId);
    }

    return task;
  }

  /**
   * 初始化Excel导入任务
   * 创建新的导入任务并存储到Session中
   *
   * @param taskId 任务ID，不能为null
   */
  public static void initUploadTask(String taskId) {
    ExcelImportBo result = new ExcelImportBo();
    result.setId(taskId);

    StpUtil.getSession().set(StringUtil.format(UPLOAD_TASK_KEY, taskId), result);
  }

  /**
   * 设置导入进度
   * 更新指定任务的导入进度
   *
   * @param id 任务ID，不能为null
   * @param process 进度值（0-100），不能为null
   */
  public static void setProcess(String id, Integer process) {
    String key = StringUtil.format(UPLOAD_TASK_KEY, id);
    ExcelImportBo task = (ExcelImportBo) StpUtil.getSession().get(key);
    task.setProcess(process);
    StpUtil.getSession().set(key, task);
  }

  /**
   * 设置成功进度
   * 更新指定任务的成功处理进度
   *
   * @param id 任务ID，不能为null
   * @param process 成功进度值（0-100），不能为null
   */
  public static void setSuccessProcess(String id, Integer process) {
    String key = StringUtil.format(UPLOAD_TASK_KEY, id);
    ExcelImportBo task = (ExcelImportBo) StpUtil.getSession().get(key);
    task.setSuccessProcess(process);
    StpUtil.getSession().set(key, task);
  }

  /**
   * 添加提示信息
   * 为指定任务添加提示信息，最多保存15条
   *
   * @param id 任务ID，不能为null
   * @param msg 提示信息，可以为null或空
   * @return true-添加成功，false-提示信息数量已达上限
   */
  public static boolean addTipMsg(String id, String msg) {
    if (StringUtil.isBlank(msg)) {
      return true;
    }
    String key = StringUtil.format(UPLOAD_TASK_KEY, id);
    ExcelImportBo task = (ExcelImportBo) StpUtil.getSession().get(key);
    if (task.getTipMsgs().size() >= 15) {
      return false;
    }
    task.getTipMsgs().add(msg);
    StpUtil.getSession().set(key, task);
    return true;
  }

  /**
   * 设置错误状态
   * 标记指定任务是否包含错误
   *
   * @param id 任务ID，不能为null
   * @param hasError 是否有错误，不能为null
   */
  public static void setHasError(String id, Boolean hasError) {
    String key = StringUtil.format(UPLOAD_TASK_KEY, id);
    ExcelImportBo task = (ExcelImportBo) StpUtil.getSession()
        .get(StringUtil.format(UPLOAD_TASK_KEY, id));
    task.setHasError(hasError);
    StpUtil.getSession().set(key, task);
  }

  /**
   * 标记任务完成
   * 将指定任务标记为已完成状态
   *
   * @param id 任务ID，不能为null
   */
  public static void finished(String id) {
    String key = StringUtil.format(UPLOAD_TASK_KEY, id);
    ExcelImportBo task = (ExcelImportBo) StpUtil.getSession()
        .get(StringUtil.format(UPLOAD_TASK_KEY, id));
    task.setFinished(Boolean.TRUE);
    StpUtil.getSession().set(key, task);
  }

  /**
   * 设置任务数据
   * 为指定任务设置处理结果数据
   *
   * @param id 任务ID，不能为null
   * @param data 任务数据，可以为null
   */
  public static void setData(String id, Object data) {
    String key = StringUtil.format(UPLOAD_TASK_KEY, id);
    ExcelImportBo task = (ExcelImportBo) StpUtil.getSession()
        .get(StringUtil.format(UPLOAD_TASK_KEY, id));
    task.setData(data);
    StpUtil.getSession().set(key, task);
  }
}
