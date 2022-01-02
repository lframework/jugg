package com.lframework.starter.mybatis.aop;

import com.lframework.common.utils.ArrayUtil;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.vo.CreateOpLogsVo;
import com.lframework.starter.web.components.security.AbstractUserDetails;
import com.lframework.starter.web.utils.SecurityUtil;
import com.lframework.starter.web.utils.SpelUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 *
 * OpLog切面
 *
 * @author zmj
 */
@Slf4j
@Aspect
@Component
@ConditionalOnProperty(value = "op-logs.enabled", matchIfMissing = true)
public class OpLogAspector {

    @Pointcut("@annotation(com.lframework.starter.mybatis.annotations.OpLog)")
    public void opLogCutPoint() {

    }

    @Around(value = "opLogCutPoint()")
    public Object opLog(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            //生成logId，多层嵌套时，隔离不同bean的数据
            String logId = IdUtil.getId();
            OpLogUtil.init(logId);

            AbstractUserDetails currentUser = SecurityUtil.getCurrentUser();

            Object value = joinPoint.proceed();

            if (currentUser != null) {
                try {
                    //获取方法的参数名和参数值
                    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
                    List<String> paramNameList = Arrays.asList(methodSignature.getParameterNames());
                    List<Object> paramList = Arrays.asList(joinPoint.getArgs());

                    //将方法的参数名和参数值一一对应的放入上下文中
                    EvaluationContext ctx = SpelUtil.buildContext();
                    for (int i = 0; i < paramNameList.size(); i++) {
                        ctx.setVariable(paramNameList.get(i), paramList.get(i));
                    }

                    //将返回值放入上下文中
                    ctx.setVariable("_result", value);

                    Map<String, String> variables = OpLogUtil.getVariables();
                    if (!CollectionUtil.isEmpty(variables)) {
                        variables.forEach((k, v) -> {
                            ctx.setVariable(k, v);
                        });
                    }

                    // 解析SpEL表达式获取结果
                    Object[] params;
                    OpLog opLog = methodSignature.getMethod().getAnnotation(OpLog.class);
                    if (!ArrayUtil.isEmpty(opLog.params())) {
                        params = new Object[opLog.params().length];
                        for (int i = 0; i < opLog.params().length; i++) {
                            String param = opLog.params()[i];
                            Object p = SpelUtil.parse(param, ctx);
                            params[i] = p;
                        }
                    } else {
                        params = new String[0];
                    }

                    List<String[]> paramsList = new ArrayList<>();
                    //循环format
                    if (opLog.loopFormat() && Arrays.stream(params).anyMatch(t -> t instanceof Collection)) {
                        String[] strParams = new String[params.length];
                        //collectionIndex的索引
                        List<Integer> collectionIndexes = new ArrayList<>();
                        for (int i = 0; i < params.length; i++) {
                            //先处理不是Collection的元素
                            if (params[i] instanceof Collection) {
                                collectionIndexes.add(i);
                                continue;
                            }
                            strParams[i] = params[i] == null ? null : params[i].toString();
                        }

                        paramsList.add(strParams);

                        if (!CollectionUtil.isEmpty(collectionIndexes)) {
                            //将所有的collection组合，例：collection1的size是2 collection2的size是3 则组合后的条数为2*3=6
                            for (Integer collectionIndex : collectionIndexes) {
                                List<String[]> tmpParamsList = new ArrayList<>();
                                for (String[] paramsArr : paramsList) {

                                    Collection collection = (Collection) params[collectionIndex];
                                    for (Object o : collection) {
                                        String[] tmp = new String[paramsArr.length];
                                        for (int j = 0; j < paramsArr.length; j++) {
                                            if (j == collectionIndex) {
                                                tmp[j] = o == null ? null : o.toString();
                                            } else {
                                                tmp[j] = paramsArr[j];
                                            }
                                        }

                                        tmpParamsList.add(tmp);
                                    }
                                }

                                paramsList.clear();
                                paramsList.addAll(tmpParamsList);
                            }
                        } else {
                            paramsList.add(strParams);
                        }
                    } else {
                        String[] strParams = new String[params.length];
                        for (int i = 0; i < params.length; i++) {
                            strParams[i] = params[i] == null ? null : params[i].toString();
                        }
                        paramsList.add(strParams);
                    }

                    for (String[] strArr : paramsList) {
                        CreateOpLogsVo vo = new CreateOpLogsVo();
                        vo.setName(StringUtil.format(opLog.name(), strArr));
                        vo.setLogType(opLog.type().getCode());
                        vo.setCreateBy(currentUser.getId());
                        vo.setExtra(OpLogUtil.getExtra());
                        vo.setIp(currentUser.getIp());

                        OpLogUtil.addLog(vo);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
            return value;
        } finally {
            OpLogUtil.clear();
        }
    }
}
