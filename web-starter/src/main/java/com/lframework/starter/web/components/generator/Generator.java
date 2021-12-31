package com.lframework.starter.web.components.generator;

import com.lframework.starter.web.components.code.GenerateCodeType;

/**
 * 单号生成器
 */
public interface Generator {

    /**
     * 获取类型
     * @return
     */
    GenerateCodeType getType();

    /**
     * 生成code
     * @return
     */
    String generate();
}
