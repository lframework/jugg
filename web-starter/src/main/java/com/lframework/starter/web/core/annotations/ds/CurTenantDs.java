package com.lframework.starter.web.core.annotations.ds;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.lframework.starter.web.core.constants.MyBatisStringPool;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DS(MyBatisStringPool.DS_KEY_CUR_TENANT)
public @interface CurTenantDs {

}
