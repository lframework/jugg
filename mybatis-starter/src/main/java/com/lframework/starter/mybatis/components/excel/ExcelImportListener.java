package com.lframework.starter.mybatis.components.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.ArrayUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.ReflectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.utils.TransactionUtil;
import com.lframework.starter.web.components.excel.ExcelEventListener;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.starter.web.service.SysParameterService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.ExcelImportUtil;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.TransactionStatus;

/**
 * Excel导入监听器 主要是用于单表头的Excel文件导入，Model的每个字段都需要标注@ExcelProperty并且不指定index,order属性
 */
@Slf4j
public abstract class ExcelImportListener<T extends ExcelModel> extends ExcelEventListener<T> {

  private final SysParameterService sysParameterService = ApplicationUtil.getBean(
      SysParameterService.class);
  /**
   * 数据
   */
  @Getter
  protected List<T> datas = new ArrayList<>();
  /**
   * 总行数
   */
  protected int totalRows;
  /**
   * 导入任务ID
   */
  protected String taskId;
  /**
   * 限制的最大行数
   */
  private Integer limitRows;
  /**
   * 已解析总行数
   */
  private int currentRows;
  /**
   * 是否存在错误
   */
  private boolean hasError;
  /**
   * 是否需要中断
   */
  private boolean interrupt;

  @Override
  public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
    this.totalRows = Math.max(0, context.readSheetHolder().getApproximateTotalRowNumber() - 1);
    if (limitRows == null) {
      this.limitRows = Integer.valueOf(
          sysParameterService.findRequiredByKey("excel-import.max-size"));
    }

    if (this.totalRows == 0) {
      this.interrupt = true;
      throw new DefaultClientException("导入文件不能为空，请检查");
    }

    if (this.totalRows > limitRows) {
      this.interrupt = true;
      throw new DefaultClientException("一次最多允许导入" + limitRows + "条");
    }
    Class<T> clazz = context.readWorkbookHolder().getClazz();
    if (clazz != null) {
      this.checkHeadMap(headMap, clazz);
    }

    this.doInvokeHeadMap(headMap, context);
  }

  protected void checkHeadMap(Map<Integer, String> headMap, Class<T> clazz) {
    Field[] fields = ReflectUtil.getFields(clazz);
    for (int i = 0; i < fields.length; i++) {
      Field field = fields[i];
      ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
      if (excelProperty != null) {
        String headerName =
            ArrayUtil.isEmpty(excelProperty.value()) ? null : excelProperty.value()[0];
        if (!StringUtil.equals(headerName, headMap.get(i))) {
          throw new DefaultClientException("第" + (i + 1) + "列应为【" + headerName + "】，请检查文件");
        }
      }
    }
  }

  protected void doInvokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
  }

  @Override
  public void invoke(T data, AnalysisContext context) {

    if (this.interrupt) {
      return;
    }

    if (!this.hasError) {
      this.datas.add(data);
    }

    this.currentRows++;

    this.setProcess(this.totalRows == 0 ? 100
        : Math.min(NumberUtil.mul(NumberUtil.div(this.currentRows, this.totalRows), 100).intValue(),
            100));

    this.doInvoke(data, context);
  }

  protected abstract void doInvoke(T data, AnalysisContext context);

  protected void setProcess(Integer process) {
    ExcelImportUtil.setProcess(this.taskId, process);
  }

  protected void setSuccessProcess(Integer process) {
    ExcelImportUtil.setSuccessProcess(this.taskId, process);
  }

  protected void setSuccessProcessByIndex(Integer curIndex) {
    this.setSuccessProcess(this.totalRows == 0 ? 100
        : Math.min(NumberUtil.mul(NumberUtil.div(curIndex + 1, this.totalRows), 100).intValue(),
            100));
  }

  protected boolean addTipMsg(String msg) {
    if (StringUtil.isBlank(msg)) {
      return true;
    }

    return ExcelImportUtil.addTipMsg(this.taskId, msg);
  }

  @Override
  public void onException(Exception exception, AnalysisContext context) throws Exception {
    log.error(exception.getMessage(), exception);
    if (!this.addTipMsg(exception.getMessage())) {
      this.interrupt = true;
    }
    this.hasError = true;
    ExcelImportUtil.setHasError(this.taskId, true);
  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext context) {
    try {
      if (this.hasError || this.interrupt) {
        return;
      }

      this.setProcess(100);

      if (this.requireTransaction()) {
        TransactionStatus transactionStatus = TransactionUtil.getTransaction();
        try {
          this.afterAllAnalysed(context);
          TransactionUtil.commit(transactionStatus);
        } catch (Throwable throwable) {
          TransactionUtil.rollback(transactionStatus);
          if (throwable instanceof Exception) {
            try {
              this.onException((Exception) throwable, context);
            } catch (Exception e) {
              throw new RuntimeException(e);
            }
          }
          throw throwable;
        }
      } else {
        this.afterAllAnalysed(context);
      }
      try {
        this.doComplete();
      } catch (Exception e) {
        try {
          this.onException(e, context);
        } catch (Exception ex) {
          throw new RuntimeException(e);
        }
      }
    } finally {
      ExcelImportUtil.finished(this.taskId);
    }
  }

  /**
   * 事务需要开启事务
   *
   * @return
   */
  protected boolean requireTransaction() {
    return true;
  }

  protected abstract void afterAllAnalysed(AnalysisContext context);

  public void setTaskId(String taskId) {
    this.taskId = taskId;

    ExcelImportUtil.initUploadTask(taskId);
  }

  protected void doComplete() {
  }
}
