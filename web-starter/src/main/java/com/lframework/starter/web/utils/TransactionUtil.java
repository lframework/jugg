package com.lframework.starter.web.utils;

import com.lframework.starter.web.common.utils.ApplicationUtil;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

public class TransactionUtil {

  /**
   * 获取事务
   *
   * @return
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
   *
   * @param transactionStatus
   */
  public static void commit(TransactionStatus transactionStatus) {
    PlatformTransactionManager platformTransactionManager = ApplicationUtil.getBean(
        PlatformTransactionManager.class);
    platformTransactionManager.commit(transactionStatus);
  }

  /**
   * 回滚事务
   *
   * @param transactionStatus
   */
  public static void rollback(TransactionStatus transactionStatus) {
    PlatformTransactionManager platformTransactionManager = ApplicationUtil.getBean(
        PlatformTransactionManager.class);
    platformTransactionManager.rollback(transactionStatus);
  }
}
