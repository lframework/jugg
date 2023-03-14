package com.lframework.starter.web.utils;

import cn.dev33.satoken.stp.StpUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.bo.ExcelImportBo;

public class ExcelImportUtil {

  private static final String UPLOAD_TASK_KEY = "upload_task_{}";

  public static ExcelImportBo getTask(String taskId) {
    ExcelImportBo task = (ExcelImportBo) StpUtil.getSession()
        .get(StringUtil.format(UPLOAD_TASK_KEY, taskId));
    if (task == null) {
      task = new ExcelImportBo();
      task.setId(taskId);
    }

    return task;
  }

  public static void initUploadTask(String taskId) {
    ExcelImportBo result = new ExcelImportBo();
    result.setId(taskId);

    StpUtil.getSession().set(StringUtil.format(UPLOAD_TASK_KEY, taskId), result);
  }

  /**
   * 设置进度
   *
   * @param id
   * @param process
   */
  public static void setProcess(String id, Integer process) {
    String key = StringUtil.format(UPLOAD_TASK_KEY, id);
    ExcelImportBo task = (ExcelImportBo) StpUtil.getSession().get(key);
    task.setProcess(process);
    StpUtil.getSession().set(key, task);
  }

  /**
   * 设置进度
   *
   * @param id
   * @param process
   */
  public static void setSuccessProcess(String id, Integer process) {
    String key = StringUtil.format(UPLOAD_TASK_KEY, id);
    ExcelImportBo task = (ExcelImportBo) StpUtil.getSession().get(key);
    task.setSuccessProcess(process);
    StpUtil.getSession().set(key, task);
  }

  /**
   * 添加提示信息
   *
   * @param id
   * @param msg
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
   * 设置错误
   *
   * @param id
   */
  public static void setHasError(String id, Boolean hasError) {
    String key = StringUtil.format(UPLOAD_TASK_KEY, id);
    ExcelImportBo task = (ExcelImportBo) StpUtil.getSession()
        .get(StringUtil.format(UPLOAD_TASK_KEY, id));
    task.setHasError(hasError);
    StpUtil.getSession().set(key, task);
  }

  /**
   * 设置完成
   *
   * @param id
   */
  public static void finished(String id) {
    String key = StringUtil.format(UPLOAD_TASK_KEY, id);
    ExcelImportBo task = (ExcelImportBo) StpUtil.getSession()
        .get(StringUtil.format(UPLOAD_TASK_KEY, id));
    task.setFinished(Boolean.TRUE);
    StpUtil.getSession().set(key, task);
  }

  /**
   * 设置数据
   *
   * @param id
   */
  public static void setData(String id, Object data) {
    String key = StringUtil.format(UPLOAD_TASK_KEY, id);
    ExcelImportBo task = (ExcelImportBo) StpUtil.getSession()
        .get(StringUtil.format(UPLOAD_TASK_KEY, id));
    task.setData(data);
    StpUtil.getSession().set(key, task);
  }
}
