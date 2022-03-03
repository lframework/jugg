package com.lframework.starter.web.components.generator.impl;

import com.lframework.starter.web.components.code.GenerateCodeType;
import com.lframework.starter.web.components.generator.Generator;
import org.springframework.stereotype.Component;

/**
 * 默认雪花算法单号生成器
 *
 * @author zmj
 */
@Component
public class DefaultSnowFlakeGenerator extends AbstractSnowFlakeGenerator implements Generator {

    @Override
    public GenerateCodeType getType() {

        return GenerateCodeType.SNOW_FLAKE;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}
