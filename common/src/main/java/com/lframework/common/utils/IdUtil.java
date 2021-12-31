package com.lframework.common.utils;

/**
 * ID工具类
 *
 * @author zmj
 */
public class IdUtil {

    /**
     * 获取ID（UUID）
     * @return
     */
    public static String getId() {

        return cn.hutool.core.util.IdUtil.fastSimpleUUID();
    }
}
