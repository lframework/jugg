package com.lframework.starter.web.core.utils;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

/**
 * 事务工具类
 * 提供手动事务管理功能，支持编程式事务控制
 * 包括事务获取、提交、回滚等功能
 *
 * @author lframework@163.com
 */
public class TransactionUtil {

  /**
   * 获取事务状态
   * 创建新的事务并返回事务状态对象
   *
   * @return 事务状态对象
   */
  public static TransactionStatus getTransaction() {
    PlatformTransactionManager platformTransactionManager = ApplicationUtil.getBean(
        PlatformTransactionManager.class);
    TransactionDefinition transactionDefinition = ApplicationUtil.getBean(
        TransactionDefinition.class);

    return platformTransactionManager.getTransaction(transactionDefinition);
  }

  /**
   * 提交事务
   * 提交指定的事务状态
   *
   * @param transactionStatus 事务状态对象，不能为null
   */
  public static void commit(TransactionStatus transactionStatus) {
    PlatformTransactionManager platformTransactionManager = ApplicationUtil.getBean(
        PlatformTransactionManager.class);
    platformTransactionManager.commit(transactionStatus);
  }

  /**
   * 回滚事务
   * 回滚指定的事务状态
   *
   * @param transactionStatus 事务状态对象，不能为null
   */
  public static void rollback(TransactionStatus transactionStatus) {
    PlatformTransactionManager platformTransactionManager = ApplicationUtil.getBean(
        PlatformTransactionManager.class);
    platformTransactionManager.rollback(transactionStatus);
  }
}
