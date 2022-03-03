package com.lframework.starter.web.components.generator.impl;

import com.lframework.common.utils.IdWorker;
import com.lframework.starter.web.components.generator.Generator;
import com.lframework.starter.web.utils.ApplicationUtil;

/**
 * 单号生成器
 *
 * @author zmj
 */
public abstract class AbstractSnowFlakeGenerator extends AbstractGenerator implements Generator {

    @Override
    public String generate() {

        IdWorker idWorker = ApplicationUtil.getBean(IdWorker.class);
        return getPreffix() + idWorker.nextIdStr();
    }

    protected String getPreffix() {

        return "";
    }
}
