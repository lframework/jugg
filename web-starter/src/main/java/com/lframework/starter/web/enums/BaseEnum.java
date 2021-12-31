package com.lframework.starter.web.enums;

import java.io.Serializable;

/**
 * Enum基类
 * @param <T>
 *
 * @author zmj
 */
public interface BaseEnum<T extends Serializable> extends Serializable {

    /**
     * 获取枚举值
     * @return
     */
    T getCode();

    /**
     * 获取描述
     * @return
     */
    String getDesc();
}
