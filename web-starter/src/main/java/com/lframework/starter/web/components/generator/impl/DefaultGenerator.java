package com.lframework.starter.web.components.generator.impl;

import com.lframework.starter.web.components.code.GenerateCodeType;
import com.lframework.starter.web.components.generator.Generator;
import org.springframework.stereotype.Component;

/**
 * 默认单号生成器
 *
 * @author zmj
 */
@Component
public class DefaultGenerator extends AbstractGenerator implements Generator {

    @Override
    public GenerateCodeType getType() {

        return GenerateCodeType.DEFAULT;
    }
}
