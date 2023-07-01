package com.lframework.starter.web.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * 事务管理器配置
 *
 * @author zmj
 */
@Configuration
@EnableTransactionManagement
public class TransactionAutoConfiguration {

  private static final String AOP_POINTCUT_EXPRESSION = "execution (* com.lframework..*.*Service.add*(..)) || execution (* com.lframework..*.*Service.save*(..)) || execution (* com.lframework..*.*Service.insert*(..)) || execution (* com.lframework..*.*Service.update*(..)) || execution (* com.lframework..*.*Service.delete*(..)) || execution (* com.lframework..*.*Service.find*(..)) || execution (* com.lframework..*.*Service.select*(..))";

  @Autowired
  private PlatformTransactionManager transactionManager;

  @Bean
  public TransactionInterceptor txAdvice() {

    NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
    /* 当前没有事务：只有select，非事务执行；有update，insert，delete操作，自动提交；
     * 当前有事务：如果有update，insert，delete操作，支持当前事务 */
    RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
    readOnlyTx.setReadOnly(true);
    readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
    /* 当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务 */
    RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
    requiredTx.setRollbackRules(
        Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
    requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    // requiredTx.setTimeout(TX_METHOD_TIMEOUT);
    Map<String, TransactionAttribute> txMap = new HashMap<>();
    txMap.put("add*", requiredTx);
    txMap.put("save*", requiredTx);
    txMap.put("insert*", requiredTx);

    txMap.put("update*", requiredTx);
    txMap.put("delete*", requiredTx);

    txMap.put("find*", readOnlyTx);
    txMap.put("select*", readOnlyTx);
    source.setNameMap(txMap);
    TransactionInterceptor txAdvice = new TransactionInterceptor(transactionManager, source);
    return txAdvice;
  }

  @Bean
  public Advisor txAdviceAdvisor(TransactionInterceptor txAdvice) {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
    return new DefaultPointcutAdvisor(pointcut, txAdvice);
  }
}
