package com.lframework.starter.web.vo;

/**
 * Vo基类
 *
 * @author zmj
 */
public interface BaseVo {

    /**
     * 手动校验参数
     * 用于SpringValidation校验完成后的手动校验
     */
    default void validate() {

    }
}
