package com.lframework.common.functions;

import java.io.Serializable;
import java.util.function.Function;

/**
 * 主要用于根据方法名获取对应的属性名
 *
 * @author zmj
 * @see com.lframework.common.utils.ReflectUtil
 */
@FunctionalInterface
public interface SFunction<T, R> extends Function<T, R>, Serializable {

}
