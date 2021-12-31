package com.lframework.starter.web.components.code;

import java.io.Serializable;

/**
 * 单号生成类型
 *
 * @author zmj
 */
public interface GenerateCodeType extends Serializable {

    /**
     * 默认的单号生成类型
     */
    GenerateCodeType DEFAULT = new DefaultGenerateCodeType();
}
